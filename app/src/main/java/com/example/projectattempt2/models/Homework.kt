package com.example.projectattempt2.models

data class Homework(
    val date: String,
    val homeworks: List<HomeworkX>,
    val pagination: Pagination
)