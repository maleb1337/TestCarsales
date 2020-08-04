package cl.maleb.testcarsales.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import cl.maleb.testcarsales.api.ApiDataSource
import cl.maleb.testcarsales.api.AppService
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CovidRepository @Inject constructor(private val service: AppService) : ApiDataSource {

    fun getCovidData(Date: String): LiveData<Result<Covid>> {
        return liveData(Dispatchers.IO) {
            emit(Result.loading())
            val result = request {
                service.getCovidData(Date)
            }

            if (result.data == null) {
                emit(Result.empty())
            } else {
                when (result.status) {
                    Result.Status.SUCCESS -> {
                        emit(Result.success(result.data.data))
                    }
                    Result.Status.ERROR -> {
                        emit(Result.error(result.data.message))
                    }
                }
            }


        }

    }

}