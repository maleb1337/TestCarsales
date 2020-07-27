package cl.maleb.testcarsales.ui.covid

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cl.maleb.testcarsales.data.Covid
import cl.maleb.testcarsales.data.CovidRepository
import cl.maleb.testcarsales.data.Result
import javax.inject.Inject
import kotlin.properties.Delegates

class CovidViewModel @Inject constructor(private val covidRepository: CovidRepository) :
    ViewModel() {

    var Date by Delegates.notNull<String>()
    var isShowProgress = MutableLiveData<Boolean>().apply { value = false }
    var isShowContent = MutableLiveData<Boolean>().apply { value = false }

    var confirmed_cases = MutableLiveData<Int>().apply { value = 0 }
    var deaths = MutableLiveData<Int>().apply { value = 0 }

    val covid by lazy { covidRepository.getCovidData(Date) }

    fun loadCovid(): LiveData<Result<Covid>> {
        return covidRepository.getCovidData(Date)
    }

    fun setVisibility(boolean: Boolean): Int {
        return when (boolean) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }


}