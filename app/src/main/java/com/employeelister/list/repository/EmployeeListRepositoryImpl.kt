package com.employeelister.list.repository

import com.employeelister.api.Employee
import com.employeelister.api.EmployeeApi
import com.employeelister.util.Result


class EmployeeListRepositoryImpl(
    private val api: EmployeeApi
): EmployeeListRepository {

    override suspend fun listEmployees(): Result<List<Employee>> {
        return try {
            val response = api.getEmployeeList()
            val employees = response.body()

            if (response.isSuccessful && !employees.isNullOrEmpty()) {
                Result.Success(employees)
            } else {
               Result.Failure(response.message())
            }

        } catch (e: Exception) {
            Result.Failure(e.message)
        }

    }
}