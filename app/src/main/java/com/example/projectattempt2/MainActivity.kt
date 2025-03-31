package com.example.projectattempt2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.projectattempt2.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        replaceFragment(HomeFragment())


        findViewById<BottomNavigationView>(R.id.bottom_nav_bar).setOnItemSelectedListener {item->

            when (item.itemId){
                R.id.home  ->{
                    replaceFragment(HomeFragment())
                }
                R.id.todo ->{
                    replaceFragment(AssignmentsFragment())
                }
            }



            return@setOnItemSelectedListener  true
        }
    }

    private fun fetchSchedule(): Thread{
        return Thread {
            val urlS = URL("https://dnevnik.egov66.ru/api/schedule?studentId=0191b872-8e03-72ea-85f4-4bbf3f7191da")
            val connection  =  urlS.openConnection() as HttpURLConnection
            if (connection.responseCode==200)
            {
                val inputSystem  =  connection.inputStream
                println(inputSystem.toString())
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction =  supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
    fun getTaskViewModel(): TaskViewModel? {
        return supportFragmentManager.findFragmentByTag("assignments_tag")
            ?.let { it as? AssignmentsFragment }?.taskViewModel
    }
}