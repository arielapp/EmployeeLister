package com.employeelister.details.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.employeelister.databinding.ActivityEmployeeDetailsBinding
import com.employeelister.details.viewModel.EmployeeDetailViewModel
import com.employeelister.util.SELECTED_EMPLOYEE_ID_KEY

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeDetailsBinding

    private val viewModel: EmployeeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val employeeId: String = intent.getStringExtra(SELECTED_EMPLOYEE_ID_KEY) ?: ""
        viewModel.displayEmployeeDetail(employeeId)

        lifecycleScope.launchWhenCreated {
            viewModel.viewState.observe(this@EmployeeDetailsActivity, {
                with(binding) {
                    this.nameTextview.text = it.employee?.employeeName ?: ""
                    this.ageTextview.text = it.employee?.employeeAge ?: ""
                    this.salaryTextview.text = it.employee?.employeeSalary ?: ""
                }
            })
        }
    }
}