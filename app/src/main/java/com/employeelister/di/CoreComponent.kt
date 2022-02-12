package com.employeelister.di

import com.employeelister.list.view.EmployeeListActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [EmployeeModule::class])
@PerActivity
interface CoreComponent {

    @Component.Builder
    interface Builder {

        fun build(): CoreComponent
    }

    fun inject(activity: EmployeeListActivity)

}