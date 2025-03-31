package com.example.projectattempt2

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.projectattempt2.databinding.LessonItemCellBinding
import com.example.projectattempt2.models.HomeworkX
import com.example.projectattempt2.models.toTaskItem
import kotlinx.coroutines.withContext


class LessonAdapter(
    private var homeworkList: List<HomeworkX>,
    private val taskViewModel: TaskViewModel
): RecyclerView.Adapter<LessonAdapter.ViewHolder>() {
    fun updateList(newList: List<HomeworkX>){
        homeworkList=newList
        notifyDataSetChanged()

    }
    inner class ViewHolder(val binding: LessonItemCellBinding):
            RecyclerView.ViewHolder(binding.root){

            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LessonItemCellBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        )
    }

    override fun getItemCount(): Int {
        return homeworkList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = homeworkList[position]
        holder.binding.apply {
            HomeworkAssigmngngn.text  = currentItem.description
            LessonName.text = currentItem.lessonName
            addtotodo.setOnClickListener{
                val taskItem = currentItem.toTaskItem()
                val context = holder.itemView.context
                taskViewModel.addTaskItem(taskItem)
                Toast.makeText(context, "Задание добавлено!", Toast.LENGTH_SHORT).show()



            }
        }

    }
}