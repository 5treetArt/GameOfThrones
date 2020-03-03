package ru.skillbranch.gameofthrones.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.LoadResult
import ru.skillbranch.gameofthrones.repositories.RootRepository

class SplashViewModel(private val app: Application) : AndroidViewModel(app) {

    private val repository = RootRepository

    private val loadResult = MutableLiveData<LoadResult<Boolean>>()

    fun syncDataIfNeed(): LiveData<LoadResult<Boolean>> = loadResult

    fun getData() = viewModelScope.launch(Dispatchers.IO) {

        if (repository.isNeedUpdate()) {
            if (!isOnline(app.applicationContext)) {
                loadResult.postValue(LoadResult.Error(""))
            }
            else {
                loadResult.postValue(LoadResult.Loading())
                repository.sync()
                loadResult.postValue(LoadResult.Success(true))
            }
        } else {
            delay(5000)
            loadResult.postValue(LoadResult.Success(true))
        }
    }

    private fun isOnline(context: Context): Boolean {
        val cm =
            context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

}