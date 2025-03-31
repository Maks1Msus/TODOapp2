package com.example.projectattempt2

import android.content.Context
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.projectattempt2.databinding.TaskItemCellBinding
import java.time.format.DateTimeFormatter

class taskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: taskItemClickListener
):RecyclerView.ViewHolder(binding.root)
{
    @RequiresApi(Build.VERSION_CODES.O)
    val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    @RequiresApi(Build.VERSION_CODES.O)
    fun bindTaskItem(taskItem: TaskItem)
    {
        binding.name.text=  taskItem.name
        if (taskItem.iscompleted()){
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
//            binding.duetime.paintFlags =  Paint.STRIKE_THRU_TEXT_FLAG
        }

        binding.completedTask.setImageResource(taskItem.imageresource())
        binding.completedTask.setColorFilter(taskItem.imagecolor(context))

        binding.taskCellContainer.setOnClickListener{
            clickListener.editTaskItem(taskItem)
        }

        binding.completedTask.setOnClickListener{
            clickListener.completeTaskItem(taskItem)
        }
        binding.deletebutton.setOnClickListener{
            clickListener.deleteTaskItem(taskItem)
        }



//        if (taskItem.dueTime() !=null){
//            binding.duetime.text  = timeFormat.format(taskItem.dueTime())
//        }
//        else
//            binding.duetime.text ==  ""
//

    }
}