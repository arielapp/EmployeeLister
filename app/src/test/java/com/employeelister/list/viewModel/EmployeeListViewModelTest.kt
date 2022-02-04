package com.employeelister.list.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.employeelister.api.Employee
import com.employeelister.list.useCase.EmployeeListUseCase
import com.employeelister.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EmployeeListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    // region Helper Fields

    private lateinit var underTest: EmployeeListViewModel

    @Mock
    private lateinit var mockEmployeeListUseCase: EmployeeListUseCase

    @Mock
    private lateinit var viewStateObserver: Observer<EmployeeListViewState>

    @Captor
    private lateinit var viewStateCaptor: ArgumentCaptor<EmployeeListViewState>

    @Mock
    private lateinit var viewEventObserver: Observer<EmployeeListViewEvent>

    @Captor
    private lateinit var viewEventCaptor: ArgumentCaptor<EmployeeListViewEvent>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {
        underTest.viewState.removeObserver(viewStateObserver)
        underTest.viewEvents.removeObserver(viewEventObserver)
    }

    // endregion Helper Fields

    // region init tests

    @Test
    fun `given valid list in useCase, when initializing the viewmodel, then verify correct values are set in viewState`() {
        runBlocking {
            // Given
            `when`(mockEmployeeListUseCase.listEmployees()).thenReturn(createValidEmployeeList())

            // When
            initViewModel()

            // Then
            with(viewStateCaptor) {
                verify(viewStateObserver, times(1)).onChanged(capture())
                assertEquals(1, allValues.size)
                val employeeList = allValues[0].employees
                assertEquals(1, employeeList.size)
                assertEquals("1", employeeList[0].id)
                assertEquals("randomName", employeeList[0].employeeName)
                assertEquals("1000$", employeeList[0].employeeSalary)
                assertEquals("23", employeeList[0].employeeAge)
                assertEquals("image", employeeList[0].profileImage)
            }
        }
    }

    @Test
    fun `given empty list in useCase, when initializing the viewmodel, then verify correct values are set in viewState`() {
        runBlocking {
            // Given
            `when`(mockEmployeeListUseCase.listEmployees()).thenReturn(emptyList())

            // When
            initViewModel()

            // Then
            with(viewStateCaptor) {
                verify(viewStateObserver, times(1)).onChanged(capture())
                assertEquals(1, allValues.size)
                val employeeList = allValues[0].employees
                assertEquals(0, employeeList.size)
            }
        }
    }

    // endregion init tests

    // region employee Item Clicked tests

    @Test
    fun `given valid string id, when employee item is clicked, then launch correct event`() {
        runBlocking {
            // Given
            `when`(mockEmployeeListUseCase.listEmployees()).thenReturn(emptyList())
            initViewModel()
            val employeeId = "1"
            // When
            underTest.employeeItemClicked(employeeId)

            // Then
            with(viewEventCaptor) {
                verify(viewEventObserver, times(1)).onChanged(capture())
                assertEquals(1, allValues.size)
                val event = allValues[0]
                assertTrue(event is EmployeeListViewEvent.ItemClicked)
                assertEquals("1", (event as EmployeeListViewEvent.ItemClicked).id)
            }
        }
    }

    @Test
    fun `given empty string id, when employee item is clicked, then do not launch event`() {
        runBlocking {
            // Given
            `when`(mockEmployeeListUseCase.listEmployees()).thenReturn(emptyList())
            initViewModel()
            val employeeId = ""
            // When
            underTest.employeeItemClicked(employeeId)

            // Then
            with(viewEventCaptor) {
                verify(viewEventObserver, times(0)).onChanged(capture())
                assertEquals(0, allValues.size)
            }
        }
    }
    // endregion employee Item Clicked tests

    // region Helper Methods

    private fun initViewModel() {
        underTest = EmployeeListViewModel(
            mockEmployeeListUseCase
        ).also {
            it.viewState.observeForever(viewStateObserver)
            it.viewEvents.observeForever(viewEventObserver)
        }
    }

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