package com.employeelister.list.repository

import com.employeelister.api.Employee
import com.employeelister.util.Result

interface EmployeeListRepository {

    suspend fun listEmployees(): Result<List<Employee>>
}