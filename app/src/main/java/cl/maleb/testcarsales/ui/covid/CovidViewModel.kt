package cl.maleb.testcarsales.ui.covid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cl.maleb.testcarsales.data.Covid
import cl.maleb.testcarsales.data.CovidRepository
import cl.maleb.testcarsales.data.Result
import javax.inject.Inject

class CovidViewModel @Inject constructor(private val covidRepository: CovidRepository) :
    ViewModel() {
    private val dateLiveData = MutableLiveData<String>()
    var isShowProgress = MutableLiveData<Boolean>().apply { value = false }
    var isShowContent = MutableLiveData<Boolean>().apply { value = false }
    var isShowError = MutableLiveData<Boolean>().apply { value = false }

    var covidData = MutableLiveData<Covid>()

    fun loadingState() {
        isShowProgress.value = true
        isShowContent.value = false
        isShowError.value = false
    }

    fun successState(data: Covid?) {
        isShowProgress.value = false
        isShowContent.value = true
        isShowError.value = false
        covidData.value = data
    }

    fun errorState() {
        isShowProgress.value = false
        isShowContent.value = true
        isShowError.value = false
    }

    fun emptyState() {
        isShowProgress.value = false
        isShowContent.value = true
        isShowError.value = false
    }

    fun isConnected(boolean: Boolean) {
        if (boolean) {
            isShowError.value = false
            isShowContent.value = true
        } else {
            isShowError.value = true
            isShowContent.value = false
        }

    }

    fun fetchDate(dateString: String) {
        dateLiveData.value = dateString
    }


    // Every time that dateLiveData has another value, it calls the API.
    var covidLiveData: LiveData<Result<Covid>> = Transformations
        .switchMap(dateLiveData) {
            covidRepository.getCovidData(it)
        }


}