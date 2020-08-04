package cl.maleb.testcarsales.ui.covid

import android.view.View
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

    var confirmedCases = MutableLiveData<Int>().apply { value = 0 }
    var deaths = MutableLiveData<Int>().apply { value = 0 }
    var selectedDate = MutableLiveData<String>().apply { value = "" }

    fun loadingState() {
        isShowProgress.value = true
        isShowContent.value = false
    }

    fun successState(dateValue: String?, confirmedValue: Int?, deathsValue: Int?) {
        isShowProgress.value = false
        isShowContent.value = true
        selectedDate.value = dateValue
        confirmedCases.value = confirmedValue
        deaths.value = deathsValue
    }

    fun errorState() {
        isShowProgress.value = false
        isShowContent.value = false
    }

    fun emptyState() {
        isShowProgress.value = false
        isShowContent.value = true
    }

    fun fetchDate(dateString: String) {
        dateLiveData.value = dateString
    }

    fun setVisibility(boolean: Boolean): Int {
        return when (boolean) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    // Every time that dateLiveData has another value, it calls the API.
    var covidLiveData: LiveData<Result<Covid>> = Transformations
        .switchMap(dateLiveData) {
            covidRepository.getCovidData(it)
        }


}