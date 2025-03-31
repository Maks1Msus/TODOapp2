package com.example.projectattempt2

import android.app.Application

class TodoApplication:Application() {

    private  val  database by  lazy { TaskItemData.getDatabase(this) }
    val  repository by  lazy { TaskItemRepository(database.taskItemDao()) }
}