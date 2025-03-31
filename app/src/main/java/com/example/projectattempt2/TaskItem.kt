package com.example.projectattempt2

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.UUID

@Entity(tableName = "task_item_table")
class TaskItem (
    @ColumnInfo(name = "name")var name:String = "",
    @ColumnInfo(name = "desc")var desc:String  = "",
    @ColumnInfo(name = "dueTimeString")var dueTimeString: String?,
    @ColumnInfo(name = "completedDateString")var completedDateString: String?,
    @PrimaryKey(autoGenerate = true)var id: Int = 0
)
{
    @RequiresApi(Build.VERSION_CODES.O)
    fun  completedDate(): LocalDate? {
        return try {
            if (completedDateString  == null) null
            else LocalDate.parse(completedDateString, dateFormatter)

        }catch (e:Exception){
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun  dueTime(): LocalTime? {
        return try {
            if (completedDateString  == null) null
            else LocalTime.parse(completedDateString, timeFormatter)
        } catch (e:Exception){
            null
        }
    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun iscompleted() = completedDate() != null
    @RequiresApi(Build.VERSION_CODES.O)
    fun imageresource():  Int  = if (iscompleted()) R.drawable.icons8check else R.drawable.icons8__________________
    @RequiresApi(Build.VERSION_CODES.O)
    fun imagecolor(conext: Context): Int = if(iscompleted())  purple(conext)  else black(conext)
    private fun purple(context: Context) =  ContextCompat.getColor(context, R.color.black)
    private fun black(context: Context) =  ContextCompat.getColor(context, R.color.black)

    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME
        @RequiresApi(Build.VERSION_CODES.O)
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }

}