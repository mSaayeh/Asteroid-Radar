package com.msayeh.asteroid.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msayeh.asteroid.Asteroid
import com.msayeh.asteroid.database.AsteroidsDatabase
import com.msayeh.asteroid.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel(private val database: AsteroidsDatabase) : ViewModel() {

    private val _navigateToDetails = MutableLiveData<Asteroid?>()
    val navigateToDetails: LiveData<Asteroid?>
        get() = _navigateToDetails

    private val _status = MutableLiveData(Status.LOADING)
    val status: LiveData<Status>
        get() = _status

    private val repository = AsteroidsRepository(database)
    init {
        viewModelScope.launch {
            repository.updateCaching()
        }
    }

    val asteroids = repository.asteroids
    val imageOfTheDay = repository.imageOfTheDay

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetails.postValue(asteroid)
    }

    fun doneNavigating() {
        _navigateToDetails.postValue(null)
    }

    fun triggerDone() {
        _status.postValue(Status.DONE)
    }

    fun triggerError() {
        _status.postValue(Status.ERROR)
    }

    fun triggerLoading() {
        _status.postValue(Status.LOADING)
    }
}

enum class Status { LOADING, ERROR, DONE }