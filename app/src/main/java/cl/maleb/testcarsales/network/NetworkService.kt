package cl.maleb.testcarsales.network

import cl.maleb.testcarsales.network.model.Covid
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://covid-19-statistics.p.rapidapi.com/reports/"
const val KEY_NAME = "X-RapidAPI-KEY"
const val API_KEY = "96afa298cbmsh913f910f914494cp110c39jsn01a32d68445e"

interface NetworkService {

    @GET("total")
    fun getCovidData(
        @Query("date") dateString: String
    ): Call<Covid>
}