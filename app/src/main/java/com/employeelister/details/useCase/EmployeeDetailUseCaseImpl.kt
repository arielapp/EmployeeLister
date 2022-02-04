package com.employeelister.details.useCase

import com.employeelister.api.Employee
import com.employeelister.details.repository.EmployeeDetailRepository

class EmployeeDetailUseCaseImpl(
    private val employeeDetailRepository: EmployeeDetailRepository
): EmployeeDetailUseCase {

    override suspend fun getEmployeeById(id: String): Employee? {
      return employeeDetailRepository.getEmployeeById(id).data
    }
}