package com.employeelister.details.useCase

import com.employeelister.api.Employee
import com.employeelister.details.repository.EmployeeDetailRepository
import com.employeelister.util.Result
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class EmployeeDetailUseCaseImplTest {

    // region Helper Fields

    private lateinit var underTest: EmployeeDetailUseCaseImpl

    @Mock
    private lateinit var mockEmployeeDetailRepository: EmployeeDetailRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        underTest = EmployeeDetailUseCaseImpl(
            mockEmployeeDetailRepository
        )

    }

    // endregion Helper Fields

    // region get Employee tests

    @Test
    fun `given success result with valid data, when getting employee by id, then return correct results`() {
        runBlocking {
            // Given
            `when`(mockEmployeeDetailRepository.getEmployeeById("1")).thenReturn(
                Result.Success(
                    Employee(
                        "1",
                        "name",
                        "230$",
                        "30",
                        "profileImage"
                    )
                )
            )
            val id = "1"
            // When
            val actualValue = underTest.getEmployeeById(id)

            // Then
            assertNotNull(actualValue)
            with(actualValue) {
                assertEquals("1", id)
                assertEquals("name", this?.employeeName)
                assertEquals("230$", this?.employeeSalary)
                assertEquals("30", this?.employeeAge)
                assertEquals("profileImage", this?.profileImage)
            }
        }
    }

    @Test
    fun `given failure result, when getting employee by id, then return null data`() {
        runBlocking {
            // Given
            `when`(mockEmployeeDetailRepository.getEmployeeById("1")).thenReturn(
                Result.Failure(
                    null
                )
            )
            val id = "1"
            // When
            val actualValue = underTest.getEmployeeById(id)

            // Then
            assertNull(actualValue)
        }
    }

    // endregion get Employee tests

    // region Helper Methods
    // endregion Helper Methods

}