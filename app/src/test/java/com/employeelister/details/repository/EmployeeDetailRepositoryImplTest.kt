package com.employeelister.details.repository

import com.employeelister.api.Employee
import com.employeelister.api.EmployeeApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class EmployeeDetailRepositoryImplTest {

    // region Helper Fields

    private lateinit var underTest: EmployeeDetailRepositoryImpl

    @Mock
    private lateinit var mockEmployeeApi: EmployeeApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        underTest = EmployeeDetailRepositoryImpl(mockEmployeeApi)
    }

    // endregion Helper Fields

    // region get employee by id tests

    @Test
    fun `given success response with valid data, when getting employee by id, then return correct values`() {
        runBlocking {
            // Given
            `when`(mockEmployeeApi.getEmployeeById("1")).thenReturn(
                Response.success(
                    Employee(
                        "1",
                        "name",
                        "100$",
                        "26",
                        "image"
                    )
                )
            )

            val id = "1"
            // When
            val actualValue = underTest.getEmployeeById(id)

            // Then
            assertNotNull(actualValue.data)
            with(actualValue.data) {
                assertEquals("1", this?.id)
                assertEquals("name", this?.employeeName)
                assertEquals("100$", this?.employeeSalary)
                assertEquals("26", this?.employeeAge)
                assertEquals("image", this?.profileImage)
            }

        }
    }

    @Test
    fun `given success response with wrong id, when getting employee by id, then return no values and error message`() {
        runBlocking {
            // Given
            val id = "2"
            // When
            val actualValue = underTest.getEmployeeById(id)

            // Then
            assertNull(actualValue.data)
            assertNull(actualValue.message)

        }
    }

    @Test
    fun `given success response with no data, when getting employee by id, then return no values and error message`() {
        runBlocking {
            // Given
            `when`(mockEmployeeApi.getEmployeeById("1")).thenReturn(
                Response.success(null)
            )

            val id = "1"
            // When
            val actualValue = underTest.getEmployeeById(id)

            // Then
            assertNull(actualValue.data)
            assertEquals("OK", actualValue.message)

        }
    }

    @Test
    fun `given error, when getting employee by id, then return no values and error message`() {
        runBlocking {
            // Given
            `when`(mockEmployeeApi.getEmployeeById("1")).thenReturn(
                Response.error(
                    400, ResponseBody.create(null, "no content")
                )
            )

            val id = "1"
            // When
            val actualValue = underTest.getEmployeeById(id)

            // Then
            assertNull(actualValue.data)
            assertEquals("Response.error()", actualValue.message)
        }
    }

    // endregion get employee by id tests

    // region Helper Methods
    // endregion Helper Methods

}