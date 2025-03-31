package com.example.projectattempt2

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.projectattempt2.databinding.FragmentNewTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)



class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTaskSheetBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    private var dueTime: LocalTime? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireHost()
        if (taskItem != null){
            binding.taskTextview.text="Изменить задачу"
            val editable =  Editable.Factory.getInstance()
            binding.Asname.text =  editable.newEditable(taskItem!!.name)
            binding.Asdesc.text = editable.newEditable(taskItem!!.desc)
            if (taskItem!!.dueTime() != null){
                dueTime = taskItem!!.dueTime()!!
                updateTimeButtonText()
            }
        }
        else
        {
            binding.taskTextview.text = "Новая задача"
        }

        binding.savebutton.setOnClickListener{
            saveAction()
        }
        binding.savetime.setOnClickListener{
            openTimePicker()
        }
    }

    private fun openTimePicker() {
        if (dueTime==null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener{ _,selectedHour,selectedMinute ->
            dueTime = LocalTime.of(selectedHour,selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity,listener,dueTime!!.hour,dueTime!!.minute,true)
        dialog.setTitle("Task Due")
        dialog.show()
    }

    private fun updateTimeButtonText() {
        binding.savetime.text =  String.format( "%02d:%02d",dueTime!!.hour,dueTime!!.minute)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?):View{
        binding = FragmentNewTaskSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    private fun saveAction() {
        val name = binding.Asname.text.toString()
        val desc = binding.Asdesc.text.toString()
        val dueTimeString = if(dueTime ==  null) null else TaskItem.timeFormatter.format(dueTime)
        if (taskItem==null)
        {
            val newTask = TaskItem(name,desc,dueTimeString,null)
            taskViewModel.addTaskItem(newTask)
        }
        else{
            taskItem!!.name=name
            taskItem!!.desc=desc
            taskItem!!.dueTimeString =dueTimeString
            taskViewModel.updateTaskItem(taskItem!!)
        }
        binding.Asname.setText("")
        binding.Asdesc.setText("")
        dismiss()
    }

}


