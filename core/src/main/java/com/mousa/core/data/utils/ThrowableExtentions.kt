package com.mousa.core.data.utils

import com.google.gson.JsonParseException
import com.mousa.core.domain.util.NetworkError
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toFailure(): NetworkError {
    return when (this) {
        is HttpException -> {
            Timber.e(this, "HTTP Exception: Code ${code()}")
            when (code()) {
                401 -> NetworkError.Unauthorized()
                in 400 until 500 -> NetworkError.Validation(message())
                in 500 until 600 -> NetworkError.ServerError()
                else -> NetworkError.General(message())
            }
        }

        is ConnectException, is UnknownHostException, is SocketTimeoutException, is IOException -> {
            Timber.e(this, "Network Error: ${this.localizedMessage.orEmpty()}")
            NetworkError.NetworkConnection()
        }

        is JsonParseException -> {
            Timber.e(this, "JSON Parsing Error")
            NetworkError.ServerError()
        }

        else -> {
            Timber.e(this, "General Error")
            NetworkError.General(message.orEmpty())
        }
    }
}