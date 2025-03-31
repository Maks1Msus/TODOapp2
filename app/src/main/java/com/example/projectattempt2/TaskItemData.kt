package com.example.projectattempt2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskItem::class], version = 1, exportSchema = false)
abstract class TaskItemData:RoomDatabase() {

    abstract fun taskItemDao():  TaskItemDao

    companion object{
        @Volatile
        private var INSTANCE: TaskItemData? =  null

        fun getDatabase(context: Context): TaskItemData{
            return  INSTANCE  ?: synchronized(this)
            {
                val instance  =  Room.databaseBuilder(
                    context.applicationContext,
                    TaskItemData::class.java,
                    "task_item_database"
                ).build()
                INSTANCE= instance
                instance
            }
        }
    }
}