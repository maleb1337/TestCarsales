package cl.maleb.testcarsales.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import cl.maleb.testcarsales.network.model.Covid
import cl.maleb.testcarsales.repository.HomeActivityRepository

class HomeActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HomeActivityRepository(application)
    val showProgress: LiveData<Boolean>
    val covidList: LiveData<Covid>

    init {
        this.showProgress = repository.showProgress
        this.covidList = repository.covidList
    }

    fun changeState() {
        repository.changeState()
    }

    fun searchDate(searchDate: String) {
        repository.searchDate(searchDate)
    }

}