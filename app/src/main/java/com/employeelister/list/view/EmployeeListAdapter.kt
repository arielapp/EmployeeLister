package com.employeelister.list.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.employeelister.api.Employee
import com.employeelister.databinding.EmployeeItemBinding
import com.employeelister.list.viewModel.EmployeeListViewModel

class EmployeeListAdapter(private val viewModel: EmployeeListViewModel) :
    RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder>() {


    inner class EmployeeViewHolder(val binding: EmployeeItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder(
            EmployeeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.binding.apply {
            val employee = viewModel.viewState.value?.employees?.get(position)
            this.nameTextview.text = employee?.employeeName
            this.ageTextview.text = employee?.employeeAge
            this.salaryTextview.text = employee?.employeeSalary
            this.root.setOnClickListener {
                viewModel.employeeItemClicked(employee?.id ?: "")
            }
        }
    }

    override fun getItemCount(): Int {
        return viewModel.viewState.value?.employees?.size ?: 0
    }


}