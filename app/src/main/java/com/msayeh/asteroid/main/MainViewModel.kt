package com.msayeh.asteroid.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msayeh.asteroid.Asteroid
import com.msayeh.asteroid.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _navigateToDetails = MutableLiveData<Asteroid?>()
    val navigateToDetails: LiveData<Asteroid?>
        get() = _navigateToDetails

    private val repository = AsteroidsRepository()
    init {
        viewModelScope.launch {
            repository.updateAsteroids()
        }
    }

    val asteroids = repository.asteroids

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetails.postValue(asteroid)
    }

    fun doneNavigating() {
        _navigateToDetails.postValue(null)
    }
}