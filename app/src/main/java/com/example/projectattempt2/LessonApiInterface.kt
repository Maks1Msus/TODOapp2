package com.example.projectattempt2

import com.example.projectattempt2.models.Homework
import com.example.projectattempt2.models.HomeworkX
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface LessonApiInterface {

    @GET("api/homework")
    suspend fun getHomework(
        @Query("date") date: String,
        @Query("studentId") studentId: String = "0191b872-8e03-72ea-85f4-4bbf3f7191da",
        @Header("Authorization") authorization: String = "Bearer eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNobWFjLXNoYTI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJmNjk3MmQ4NS1mMDc0LTQ2ZDQtYWQ2NS00OTRjODI0MWExY2EiLCJ0eXBlIjoiYWNjZXNzIiwidXNlcklkIjoiOWM3OGE5ZGItNmU1Ny00ZTFjLWFlNmEtYTdjM2JjYTI3YjY1IiwicGVyc29uSWQiOiJhZDEwMmFjMC1kMDMyLTQ1M2QtYWJmMy00ZWI2NTdjYTBkZGYiLCJvcmdhbml6YXRpb25JZCI6IjAwMDAwMDAwLTAwMDAtMDAwMC0wMDAwLTAwMDAwMDAwMDAwMCIsImV4cCI6MTc0MzQ0OTQwNSwiaXNzIjoiQWlzczIiLCJhdWQiOiJBaXNzMiJ9.xwM_oVW-DgAd30FpJsV_voHR9z094lDIm7pkxmymrbw",
        @Header("User-Agent") userAgent: String = "GovernorsLyceumTODO"


        ):Response<Homework>
}