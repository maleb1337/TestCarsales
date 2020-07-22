package cl.maleb.testcarsales.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-KEY", "96afa298cbmsh913f910f914494cp110c39jsn01a32d68445e")
            .build()
        return chain.proceed(request)
    }
}