package com.employeelister.details.repository

import com.employeelister.api.Employee
import com.employeelister.api.EmployeeApi
import com.employeelister.util.Result

class EmployeeDetailRepositoryImpl(
    private val api: EmployeeApi
): EmployeeDetailRepository {

    override suspend fun getEmployeeById(id: String): Result<Employee> {
        return try {
            val response = api.getEmployeeById(id)
            val employee = response.body()

            if (response.isSuccessful && employee != null) {
                Result.Success(employee)
            } else {
                Result.Failure(response.message())
            }

        } catch (e: Exception) {
            Result.Failure(e.message)
        }
    }
}