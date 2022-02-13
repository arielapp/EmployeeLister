package com.employeelister.di

import androidx.lifecycle.ViewModel
import com.employeelister.api.EmployeeApi
import com.employeelister.details.repository.EmployeeDetailRepository
import com.employeelister.details.repository.EmployeeDetailRepositoryImpl
import com.employeelister.details.useCase.EmployeeDetailUseCase
import com.employeelister.details.useCase.EmployeeDetailUseCaseImpl
import com.employeelister.details.viewModel.EmployeeDetailViewModel
import com.employeelister.list.repository.EmployeeListRepository
import com.employeelister.list.repository.EmployeeListRepositoryImpl
import com.employeelister.list.useCase.EmployeeListUseCase
import com.employeelister.list.useCase.EmployeeListUseCaseImpl
import com.employeelister.list.viewModel.EmployeeListViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider

@Module
class EmployeeModule {

    @Provides
    fun provideEmployeeApi(): EmployeeApi {
        return Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmployeeApi::class.java)
    }

    @Provides
    fun provideViewModelFactory(viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelFactory {
        return ViewModelFactory.create(viewModels)
    }

    @Provides
    @IntoMap
    @ViewModelKey(EmployeeListViewModel::class)
    fun provideEmployeeListViewModel(useCase: EmployeeListUseCase): ViewModel {
        return EmployeeListViewModel(useCase)
    }

    @Provides
    fun provideEmployeeListUseCase(repo: EmployeeListRepository): EmployeeListUseCase {
        return EmployeeListUseCaseImpl(repo)
    }

    @Provides
    fun provideEmployeeListRepository(api: EmployeeApi): EmployeeListRepository {
        return EmployeeListRepositoryImpl(api)
    }

    @Provides
    @IntoMap
    @ViewModelKey(EmployeeDetailViewModel::class)
    fun provideEmployeeDetailViewModel(useCase: EmployeeDetailUseCase): ViewModel {
        return EmployeeDetailViewModel(useCase)
    }

    @Provides
    fun provideEmployeeDetailUseCase(repo: EmployeeDetailRepository): EmployeeDetailUseCase {
        return EmployeeDetailUseCaseImpl(repo)
    }

    @Provides
    fun provideEmployeeDetailRepository(api: EmployeeApi): EmployeeDetailRepository {
        return EmployeeDetailRepositoryImpl(api)
    }
}