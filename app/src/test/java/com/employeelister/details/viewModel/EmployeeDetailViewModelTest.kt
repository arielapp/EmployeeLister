package com.employeelister.details.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.employeelister.api.Employee
import com.employeelister.details.useCase.EmployeeDetailUseCase
import com.employeelister.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EmployeeDetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    // region Helper Fields

    private lateinit var underTest: EmployeeDetailViewModel

    @Mock
    private lateinit var mockEmployeeDetailUseCase: EmployeeDetailUseCase

    @Mock
    private lateinit var viewStateObserver: Observer<EmployeeDetailViewState>

    @Captor
    private lateinit var viewStateCaptor: ArgumentCaptor<EmployeeDetailViewState>

    companion object {
        private const val VALID_EMPLOYEE_ID = "1"
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        initViewModel()
    }

    @After
    fun tearDown() {
        underTest.viewState.removeObserver(viewStateObserver)
    }

    // endregion Helper Fields

    // region init tests

    @Test
    fun `given valid employee id, when displaying employee details, then verify correct values are set in viewState`() {
        runBlocking {
            // Given
            Mockito.`when`(mockEmployeeDetailUseCase.getEmployeeById(VALID_EMPLOYEE_ID))
                .thenReturn(createValidEmployee())

            // When
            underTest.displayEmployeeDetail(VALID_EMPLOYEE_ID)

            // Then
            with(viewStateCaptor) {
                Mockito.verify(viewStateObserver, Mockito.times(1)).onChanged(capture())
                assertEquals(1, allValues.size)
                val employee = allValues[0].employee
                assertEquals("1", employee?.id)
                assertEquals("randomName", employee?.employeeName)
                assertEquals("1000$", employee?.employeeSalary)
                assertEquals("23", employee?.employeeAge)
                assertEquals("image", employee?.profileImage)
            }
        }
    }

    @Test
    fun `given empty employee id, when displaying employee details, then verify values are not set in viewState`() {
        runBlocking {
            // When
            underTest.displayEmployeeDetail("")

            // Then
            with(viewStateCaptor) {
                Mockito.verify(viewStateObserver, Mockito.times(0)).onChanged(capture())
                assertEquals(0, allValues.size)
            }
        }
    }

    // endregion init tests

    // region Helper Methods

    private fun initViewModel() {
        underTest = EmployeeDetailViewModel(
            mockEmployeeDetailUseCase
        ).also {
            it.viewState.observeForever(viewStateObserver)
        }
    }

    private fun createValidEmployee(): Employee {
        return Employee(
            "1",
            "randomName",
            "1000$",
            "23",
            "image"
        )

    }

    // endregion Helper Methods

}