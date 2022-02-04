package com.employeelister.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EmployeeApi {

    @GET("/employee")
    suspend fun getEmployeeList(): Response<List<Employee>>

    @GET("/employee")
    suspend fun getEmployeeById(
        @Query("id")
        id: String
    ): Response<Employee>
}