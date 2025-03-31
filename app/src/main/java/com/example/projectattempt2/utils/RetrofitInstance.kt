package com.example.projectattempt2.utils

import com.example.projectattempt2.LessonApiInterface
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // Таймаут подключения
        .readTimeout(30, TimeUnit.SECONDS)    // Таймаут чтения
        .writeTimeout(30, TimeUnit.SECONDS)   // Таймаут записи
        .protocols(listOf(Protocol.HTTP_1_1))
        .build()
    val api : LessonApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Util.BASE)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LessonApiInterface::class.java)
    }
}