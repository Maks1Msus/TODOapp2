package com.example.projectattempt2

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.projectattempt2.databinding.TaskItemCellBinding

class taskItemAdapter(
    private val taskItems: List<TaskItem>,
    private val clickListener: taskItemClickListener
): RecyclerView.Adapter<taskItemViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): taskItemViewHolder {
        val from  = LayoutInflater.from(parent.context)
        val binding =  TaskItemCellBinding.inflate(from,parent,false)
        return taskItemViewHolder(parent.context, binding, clickListener)
    }

    override fun getItemCount(): Int =  taskItems.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: taskItemViewHolder, position: Int) {
        holder.bindTaskItem(taskItems[position])
    }

}
