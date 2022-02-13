package com.employeelister.list.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.employeelister.api.Employee
import com.employeelister.list.useCase.EmployeeListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmployeeListViewModel @Inject constructor(
    private val employeeListUseCase: EmployeeListUseCase
) : ViewModel() {

    private val mutableViewStates = MutableLiveData<EmployeeListViewState>()
    val viewState = mutableViewStates

    private val mutableViewEvents = MutableLiveData<EmployeeListViewEvent>()
    val viewEvents = mutableViewEvents

    init {
        listEmployees()
    }

    private fun listEmployees() {
        var employees = emptyList<Employee>()
        Log.i("EMPLOYEE VIEW MODEL", "outside scope: $employees")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                employees = employeeListUseCase.listEmployees()
                Log.i("EMPLOYEE VIEW MODEL", "viewModelScope: $employees")

            }
            withContext(Dispatchers.Main) {
                mutableViewStates.value = EmployeeListViewState(
                    employees
                )
            }
        }
    }

    fun employeeItemClicked(id: String) {
        if (id.isNotEmpty()) {
            viewEvents.value = EmployeeListViewEvent.ItemClicked(id)
        }
    }
}