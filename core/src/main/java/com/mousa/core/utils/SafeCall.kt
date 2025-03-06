package com.mousa.core.utils

import retrofit2.Response
import timber.log.Timber

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
    return runCatching { apiCall.invoke() }
        .fold(
            onSuccess = { response ->
                when {
                    !response.isSuccessful -> {
                        Timber.e("API Error: ${response.message()}")
                        Resource.Failure(NetworkError.General(response.message()))
                    }
                    response.body() == null -> {
                        Timber.e("API Response is null")
                        Resource.Failure(NetworkError.ResponseIsNull())
                    }
                    else -> Resource.Success(response.body()!!)
                }
            },
            onFailure = { throwable ->
                Timber.e(throwable, "API Failure: ${throwable.localizedMessage.orEmpty()}")
                Resource.Failure(throwable.toFailure())
            }
        )
}

suspend fun <T> safeCall(call: suspend () -> T): Resource<T> {
    return runCatching { call.invoke() }
        .map { Resource.Success(it) }
        .getOrElse { throwable ->
            Timber.e("Database Failure", throwable.localizedMessage.orEmpty())
            Resource.Failure(throwable = throwable)
        }
}