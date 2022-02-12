package com.employeelister.list.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.employeelister.databinding.ActivityEmployeeListBinding
import com.employeelister.details.view.EmployeeDetailsActivity
import com.employeelister.di.*
import com.employeelister.list.viewModel.EmployeeListViewEvent
import com.employeelister.list.viewModel.EmployeeListViewModel
import com.employeelister.util.SELECTED_EMPLOYEE_ID_KEY
import javax.inject.Inject

class EmployeeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeListBinding

    private lateinit var employeesAdapter: EmployeeListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: EmployeeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coreComponent.inject(this)
        binding = ActivityEmployeeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory).get(EmployeeListViewModel::class.java)

        lifecycleScope.launchWhenCreated {
            with(viewModel) {
                viewState.observe(this@EmployeeListActivity, {
                    employeesAdapter = EmployeeListAdapter(viewModel)
                    with(binding.employeeListRecyclerview) {
                        layoutManager = LinearLayoutManager(this@EmployeeListActivity)
                        adapter = employeesAdapter
                    }
                })

                viewEvents.observe(this@EmployeeListActivity, {
                    when (it) {
                        is EmployeeListViewEvent.ItemClicked -> {
                            startActivity(
                                Intent(
                                    this@EmployeeListActivity,
                                    EmployeeDetailsActivity::class.java
                                ).apply {
                                    putExtra(SELECTED_EMPLOYEE_ID_KEY, it.id)
                                }
                            )
                        }
                    }
                })
            }
        }
    }

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent
            .builder()
            .build()
    }
}