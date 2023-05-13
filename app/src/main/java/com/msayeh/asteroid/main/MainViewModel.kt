package com.msayeh.asteroid.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msayeh.asteroid.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = AsteroidsRepository()
    init {
        viewModelScope.launch {
            repository.updateAsteroids()
        }
    }

    val asteroids = repository.asteroids
}