package com.employeelister.list.repository

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
class EmployeeListRepositoryImplTest {

    // region Fields

    private lateinit var underTest: EmployeeListRepositoryImpl

    @Mock
    private lateinit var mockEmployeeApi: EmployeeApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        underTest = EmployeeListRepositoryImpl(
            mockEmployeeApi
        )

    }

    // endregion Fields

    // region employee List tests

    @Test
    fun `given successful response with valid input, when listing employees, then return valid results`() {
        runBlocking {
            // Given
            `when`(mockEmployeeApi.getEmployeeList()).thenReturn(
                Response.success(createValidEmployeeList())
            )

            // When
            val actualValue = underTest.listEmployees()

            // Then
            assertNotNull(actualValue.data)
            with(actualValue.data) {
                assertEquals(1, this?.size)
                assertNotNull(this?.get(0))
                assertEquals("1", this?.get(0)?.id)
                assertEquals("randomName", this?.get(0)?.employeeName)
                assertEquals("1000$", this?.get(0)?.employeeSalary)
                assertEquals("23", this?.get(0)?.employeeAge)
                assertEquals("image", this?.get(0)?.profileImage)
            }
        }
    }

    @Test
    fun `given successful response with no data, when listing employees, then return null data and error message`() {
        runBlocking {
            // Given
            `when`(mockEmployeeApi.getEmployeeList()).thenReturn(
                Response.success(
                    emptyList()
                )
            )

            // When
            val actualValue = underTest.listEmployees()

            // Then
            assertNull(actualValue.data)
            assertEquals("OK", actualValue.message)
        }
    }

    @Test
    fun `given error response with no data, when listing employees, then return null data and error message`() {
        runBlocking {
            // Given
            `when`(mockEmployeeApi.getEmployeeList()).thenReturn(
                Response.error(
                    400,
                    ResponseBody.create(null, "error")
                )
            )

            // When
            val actualValue = underTest.listEmployees()

            // Then
            assertNull(actualValue.data)
            assertEquals("Response.error()", actualValue.message)

        }
    }
    // endregion employee List tests

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