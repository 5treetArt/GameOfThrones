package ru.skillbranch.gameofthrones.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SplashViewModel  : ViewModel() {
    fun setDataLoaded() {
        isLoaded.postValue(true)
    }

    private val isLoaded = MutableLiveData<Boolean>()
    val loaded: LiveData<Boolean> = isLoaded
}