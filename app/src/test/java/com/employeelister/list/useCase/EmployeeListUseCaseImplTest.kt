package com.employeelister.list.useCase

import com.employeelister.api.Employee
import com.employeelister.list.repository.EmployeeListRepository
import com.employeelister.util.Result
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class EmployeeListUseCaseImplTest {

    // region Helper Fields

    private lateinit var underTest: EmployeeListUseCaseImpl

    @Mock
    private lateinit var mockEmployeeListRepository: EmployeeListRepository


    @Before
    fun setup() {
        underTest = EmployeeListUseCaseImpl(
            mockEmployeeListRepository
        )

    }

    // endregion Helper Fields

    // region list Employees Tests

    @Test
    fun `given success result with valid data, when listing employees, then return correct data`() {
        runBlocking {
            // Given
            `when`(mockEmployeeListRepository.listEmployees()).thenReturn(
                Result.Success(createValidEmployeeList())
            )
            // When
            val actualValues = underTest.listEmployees()

            // Then
            with(actualValues) {
                assertEquals(1, this.size)
                assertNotNull(this[0])
                assertEquals("1", this[0].id)
                assertEquals("randomName", this[0].employeeName)
                assertEquals("1000$", this[0].employeeSalary)
                assertEquals("23", this[0].employeeAge)
                assertEquals("image", this[0].profileImage)
            }
        }
    }

    @Test
    fun `given success result with no data, when listing employees, then return empty list`() {
        runBlocking {
            // Given
            `when`(mockEmployeeListRepository.listEmployees()).thenReturn(
                Result.Success(
                    emptyList()
                )
            )
            // When
            val actualValues = underTest.listEmployees()

            // Then
            assertEquals(0, actualValues.size)
        }
    }

    @Test
    fun `given failure result, when listing employees, then return empty list`() {
        runBlocking {
            // Given
            `when`(mockEmployeeListRepository.listEmployees()).thenReturn(
                Result.Failure(
                    null
                )
            )
            // When
            val actualValues = underTest.listEmployees()

            // Then
            assertEquals(0, actualValues.size)
        }
    }

    // endregion list Employees Tests

    // region Helper Methods

    private fun createValidEmployeeList(): List<Employee> {
        return listOf(
            Employee(
                "1",
                "randomName",
                "1000$",
                "23",
                "image"
            )
        )
    }

    // endregion Helper Methods
}