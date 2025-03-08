package com.mousa.core.data.utils

import com.mousa.core.domain.util.NetworkError
import com.mousa.core.domain.util.Resource
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
    return try {
        val apiResponse = apiCall.invoke()

        if (!apiResponse.isSuccessful) {
            Timber.e("API Error: ${apiResponse.message()}")
            throw HttpException(apiResponse)
        }

        val body = apiResponse.body()
        if (body == null) {
            Timber.e("API Response is null")
            return Resource.Failure(NetworkError.ResponseIsNull())
        }

        Resource.Success(body)
    } catch (throwable: Throwable) {
        Timber.e(throwable, "API Failure: ${throwable.localizedMessage.orEmpty()}")
        Resource.Failure(throwable.toFailure())
    }
}

suspend fun <T> safeCall(call: suspend () -> T): Resource<T> {
    return runCatching { call.invoke() }
        .mapCatching {
            if (it == null) throw NullPointerException("Database returned null")
            Resource.Success(it)
        }
        .getOrElse { throwable ->
            Timber.e("Database Failure", throwable.localizedMessage.orEmpty())
            Resource.Failure(throwable = throwable)
        }
}