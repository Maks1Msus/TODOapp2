package com.example.projectattempt2

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectattempt2.databinding.FragmentHomeBinding
import com.example.projectattempt2.models.HomeworkX
import com.example.projectattempt2.utils.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
import java.io.StringReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class HomeFragment : Fragment() {
    private var selectedDate: String = getCurrentDate()
    private lateinit var binding:FragmentHomeBinding
    private lateinit var LessonAdapter:LessonAdapter
    private var HomeworkList:List<HomeworkX> = emptyList()
    private val taskViewModel: TaskViewModel by activityViewModels{
        val application = requireActivity().application as TodoApplication
        TaskItemModelFactory(application.repository)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }
    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        return String.format(
            Locale.US,
            "%04d-%02d-%02d",
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        setupDatePicker()
        binding.Datetoday.text = selectedDate
        LessonAdapter(HomeworkList, taskViewModel)
        loadHM()
    }
    private fun setupDatePicker() {
        binding.datepick.setOnClickListener {
            Log.d("DatePicker", "Кнопка нажата")
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        if (!isAdded) return
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(requireContext(), { _, y, m, d ->
            selectedDate = String.format(Locale.US,"%04d-%02d-%02d", y, m + 1, d)
            binding.Datetoday.text = selectedDate
            loadHM()
        }, year, month, day)
        datePicker.show()
    }

    private fun setupRV(){
        LessonAdapter = LessonAdapter(HomeworkList,taskViewModel)
        binding.HMWORKRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = LessonAdapter
        }
    }
    private fun loadHM(){
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                Log.d("API", "Запрос к API для даты: $selectedDate")
                val response=RetrofitInstance.api.getHomework(selectedDate)
                if(response.isSuccessful && response.body()!=null){
                    HomeworkList =  response.body()!!.homeworks
                    Log.d("API", "Получено элементов: ${HomeworkList.size}")
                    updateUI()
                }else{
                    Log.e("API", "Ошибка: ${response.code()} ${response.message()}")
                }
            }catch (e: IOException){
                Log.e("API", "Сетевая ошибка: ${e.message}")
                showToastOnMainThread("Network Error:${e.message}")

            }catch (e:HttpException){
                Log.e("API", "HTTP-ошибка: ${e.message()}")
                showToastOnMainThread("Http error: ${e.message}")
            }
        }
    }

    private fun updateUI(){
        requireActivity().runOnUiThread{
            LessonAdapter.updateList(HomeworkList)
        }
    }
    private fun showToastOnMainThread(message: String){
        activity?.runOnUiThread{
            Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
        }

    }




}