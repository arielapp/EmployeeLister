package com.employeelister.details.repository

import com.employeelister.api.Employee
import com.employeelister.util.Result

interface EmployeeDetailRepository {

    suspend fun getEmployeeById(id: String): Result<Employee>

}