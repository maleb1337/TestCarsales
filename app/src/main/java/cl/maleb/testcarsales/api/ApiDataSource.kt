package cl.maleb.testcarsales.api

import android.util.Log
import kotlinx.coroutines.coroutineScope
import cl.maleb.testcarsales.data.Result

interface ApiDataSource {
    suspend fun <T> request(
        call: suspend () -> T
    ): Result<T> = coroutineScope {
        try {
            Result.success(call())

        } catch (exception: Exception) {
            Log.e("EXCEPTION", exception.localizedMessage ?: "Some error")
            Result.error<T>(exception.localizedMessage ?: "Some error")
        }
    }
}