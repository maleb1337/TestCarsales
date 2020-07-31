package cl.maleb.testcarsales.api

import cl.maleb.testcarsales.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-KEY", BuildConfig.API_KEY)
            .build()
        return chain.proceed(request)
    }
}