package com.employeelister.details.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.employeelister.details.useCase.EmployeeDetailUseCase
import kotlinx.coroutines.launch

class EmployeeDetailViewModel(
    private val employeeDetailUseCase: EmployeeDetailUseCase
) : ViewModel() {

    private val mutableViewStates = MutableLiveData<EmployeeDetailViewState>()
    val viewState = mutableViewStates

    fun displayEmployeeDetail(id: String) {
        if (id.isNotEmpty()) {
            viewModelScope.launch {
                mutableViewStates.value = EmployeeDetailViewState(
                    employeeDetailUseCase.getEmployeeById(id)
                )
            }
        }
    }

}