package cl.maleb.testcarsales.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import cl.maleb.testcarsales.network.API_KEY
import cl.maleb.testcarsales.network.BASE_URL
import cl.maleb.testcarsales.network.KEY_NAME
import cl.maleb.testcarsales.network.NetworkService
import cl.maleb.testcarsales.network.model.Covid
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class HomeActivityRepository(val application: Application) {

    val showProgress = MutableLiveData<Boolean>()
    val covidList = MutableLiveData<Covid>()

    fun changeState() {
        showProgress.value = !(showProgress.value != null && showProgress.value!!)
    }

    fun searchDate(searchDate: String) {
        showProgress.value = true
        // Networkcall
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .header(
                    KEY_NAME,
                    API_KEY
                )

            val request = requestBuilder.build()
            chain.proceed(request)
        }


        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)

        httpClient.addNetworkInterceptor(logging)

        val okHttpClient = httpClient.build()

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        val service = retrofit.create(NetworkService::class.java)

        service.getCovidData(searchDate).enqueue(object : Callback<Covid> {
            override fun onFailure(call: Call<Covid>, t: Throwable) {
                showProgress.value = false
                Toast.makeText(application, t.toString(), Toast.LENGTH_LONG).show()
                Log.d("HomeRepository", t.message.toString())
            }

            override fun onResponse(
                call: Call<Covid>,
                response: Response<Covid>
            ) {
                Log.d("HomeRepository", "Response: ${Gson().toJson(response.body())}")
                showProgress.value = false
                covidList.value = response.body()
            }

        })


    }
}