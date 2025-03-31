package com.example.projectattempt2.models

import com.example.projectattempt2.TaskItem
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class HomeworkX(
    val description: String,
    val lessonName: String,
)

fun HomeworkX.toTaskItem(): TaskItem {
    return TaskItem(
        name = this.lessonName,
        desc = this.description,
        completedDateString = null,
        dueTimeString = LocalTime.now().toString(),


    )
}

