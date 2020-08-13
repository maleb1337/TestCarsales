package cl.maleb.testcarsales.api

import cl.maleb.testcarsales.data.Covid
import cl.maleb.testcarsales.data.Result
import retrofit2.http.GET
import retrofit2.http.Query


interface AppService {

    companion object {
        const val ENDPOINT = "https://covid-19-statistics.p.rapidapi.com/reports/"
    }

    @GET("total")
    suspend fun getCovidData(
        @Query("date") dateString: String
    ): Result<Covid>
}