package com.example.projectattempt2

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID
@RequiresApi(Build.VERSION_CODES.O)

class TaskViewModel(private val repository: TaskItemRepository):ViewModel() {
    var taskitems: LiveData<List<TaskItem>> = repository.allTaskItems.asLiveData()


    fun addTaskItem(newTask: TaskItem) = viewModelScope.launch {
            Log.d("TASK_DEBUG", "Добавление: ${newTask.name}")
            repository.insertTaskItem(newTask)

    }

    fun updateTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repository.updateTaskItem(taskItem)
    }

    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch {
        if (!taskItem.iscompleted())
            taskItem.completedDateString = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

        repository.updateTaskItem(taskItem)
    }

    fun deleteTaskItem(taskItem: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTaskItem(taskItem)
        }

    }
}

class TaskItemModelFactory(private  val repository: TaskItemRepository):ViewModelProvider.Factory
{
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(repository) as T

        throw  IllegalArgumentException("Unknown Class")
    }
}

