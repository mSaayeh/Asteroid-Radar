package com.msayeh.asteroid.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.msayeh.asteroid.database.AsteroidsDatabase

class MainViewModelFactory(private val database: AsteroidsDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(database) as T
        }
        throw IllegalArgumentException("Unable to construct given ViewModel")
    }
}