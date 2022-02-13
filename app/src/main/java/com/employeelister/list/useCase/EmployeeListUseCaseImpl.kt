package com.employeelister.list.useCase

import com.employeelister.api.Employee
import com.employeelister.list.repository.EmployeeListRepository
import javax.inject.Inject

class EmployeeListUseCaseImpl @Inject constructor(
    private val employeeListRepository: EmployeeListRepository
): EmployeeListUseCase {
    override suspend fun listEmployees(): List<Employee> {
        return employeeListRepository.listEmployees().data ?: emptyList()
    }
}