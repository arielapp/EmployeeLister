package com.employeelister.di

import android.content.Context
import java.lang.IllegalStateException

fun provideCoreComponent(applicationContext: Context): CoreComponent {
    return if (applicationContext is CoreComponentProvider) {
        (applicationContext as CoreComponentProvider).coreComponent
    } else {
        throw IllegalStateException("application does not implement CoreComponentProvider Interface")
    }
}