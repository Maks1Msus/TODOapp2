package com.example.projectattempt2

import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectattempt2.databinding.FragmentAssignmentsBinding



// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AssignmentsFragment : Fragment(), taskItemClickListener {
    private lateinit var binding: FragmentAssignmentsBinding
    private lateinit var adapter: taskItemAdapter
    val taskViewModel: TaskViewModel by activityViewModels {
        val application =  requireActivity().application as TodoApplication
        TaskItemModelFactory(application.repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentAssignmentsBinding.inflate(layoutInflater)

        binding.NewTaskButton.setOnClickListener {
            NewTaskSheet(null).show(childFragmentManager, "newTaskTag")
        }
        setRecyclerView()



        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setRecyclerView() {

        binding.todoLitsRecycleView.apply {
            val TODOfrag = this@AssignmentsFragment
            taskViewModel.taskitems.observe(viewLifecycleOwner) {
                binding.todoLitsRecycleView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = taskItemAdapter(it, TODOfrag)
                }
            }
        }






    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(childFragmentManager, "newTaskTag")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deleteTaskItem(taskItem: TaskItem) {
        taskViewModel.deleteTaskItem(taskItem)
    }

}