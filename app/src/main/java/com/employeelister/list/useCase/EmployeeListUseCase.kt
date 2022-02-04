package com.employeelister.list.useCase

import com.employeelister.api.Employee

interface EmployeeListUseCase {

    suspend fun listEmployees(): List<Employee>

}