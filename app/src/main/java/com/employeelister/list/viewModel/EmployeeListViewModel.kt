package com.employeelister.list.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.employeelister.list.useCase.EmployeeListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeListViewModel(
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
        viewModelScope.launch {
            mutableViewStates.value = EmployeeListViewState(
            employeeListUseCase.listEmployees()
            )
        }
    }

    fun employeeItemClicked(id: String) {
        if (id.isNotEmpty()) {
            viewEvents.value = EmployeeListViewEvent.ItemClicked(id)
        }
    }
}