package com.employeelister.details.useCase

import com.employeelister.api.Employee

interface EmployeeDetailUseCase {

    suspend fun getEmployeeById(id: String): Employee?
}