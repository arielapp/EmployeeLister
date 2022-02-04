package com.employeelister.api


import com.google.gson.annotations.SerializedName

data class Employee(
    @SerializedName("id")
    val id: String,
    @SerializedName("employee_name")
    val employeeName: String,
    @SerializedName("employee_salary")
    val employeeSalary: String,
    @SerializedName("employee_age")
    val employeeAge: String,
    @SerializedName("profile_image")
    val profileImage: String
)