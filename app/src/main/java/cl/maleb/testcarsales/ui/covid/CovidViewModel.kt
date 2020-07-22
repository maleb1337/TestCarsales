package cl.maleb.testcarsales.ui.covid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cl.maleb.testcarsales.data.Covid
import cl.maleb.testcarsales.data.CovidRepository
import cl.maleb.testcarsales.data.Result
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates

class CovidViewModel @Inject constructor(private val covidRepository: CovidRepository) :
    ViewModel() {

    var Date by Delegates.notNull<String>()
    var isShowProgress = MutableLiveData<Int>().apply { value = 0 }
    var isShowContent = MutableLiveData<Int>().apply { value = 0 }

    val covid by lazy { covidRepository.getCovidData(Date) }

    fun loadCovid(): LiveData<Result<Covid>> {
        return covidRepository.getCovidData(Date)
    }

    fun getCurrentDay(): String {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1
        val day = c.get(Calendar.DAY_OF_MONTH) - 1

        var date = ""
        if (day < 10) {
            if (month < 10) {
                date = "$year-0$month-0$day"
            } else {
                date = "$year-$month-0$day"
            }

        } else {
            if (month < 10) {
                date = "$year-0$month-$day"
            } else {
                date = "$year-$month-$day"
            }
        }

        return date
    }

    fun parseDateFromCalendar(dayOfMonth: Int, monthOfYear: Int, year: Int): String {
        var date = ""
        if (dayOfMonth < 10) {
            if (monthOfYear < 10) {
                date = "$year-0$monthOfYear-0$dayOfMonth"
            } else {
                date = "$year-$monthOfYear-0$dayOfMonth"
            }

        } else {
            if (monthOfYear < 10) {
                date = "$year-0$monthOfYear-$dayOfMonth"
            } else {
                date = "$year-$monthOfYear-$dayOfMonth"
            }
        }

        return date;
    }

}