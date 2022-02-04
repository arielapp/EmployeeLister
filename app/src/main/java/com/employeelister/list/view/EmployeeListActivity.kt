package com.employeelister.list.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.employeelister.databinding.ActivityEmployeeListBinding
import com.employeelister.details.view.EmployeeDetailsActivity
import com.employeelister.list.viewModel.EmployeeListViewEvent
import com.employeelister.list.viewModel.EmployeeListViewModel
import com.employeelister.util.SELECTED_EMPLOYEE_ID_KEY

class EmployeeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeListBinding

    private lateinit var employeesAdapter: EmployeeListAdapter

    private val viewModel: EmployeeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated {
            with(viewModel) {
                viewState.observe(this@EmployeeListActivity, {
                    employeesAdapter = EmployeeListAdapter(viewModel)
                    binding.employeeListRecyclerview.adapter = employeesAdapter
                })

                viewEvents.observe(this@EmployeeListActivity, {
                    when(it) {
                        is EmployeeListViewEvent.ItemClicked -> {
                            startActivity(
                                Intent(this@EmployeeListActivity, EmployeeDetailsActivity::class.java).apply {
                                    putExtra(SELECTED_EMPLOYEE_ID_KEY, it.id)
                                }
                            )
                        }
                    }
                })
            }
        }
    }
}