package com.employeelister.list.viewModel

sealed class EmployeeListViewEvent {
    data class ItemClicked(val id: String) : EmployeeListViewEvent()
}
