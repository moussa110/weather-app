package com.mousa.core.domain.util

sealed class Resource<out T> {
    data class Success<T>(val value: T) : Resource<T>()
    data class Failure(val networkError: NetworkError? = null, val throwable: Throwable? = null) : Resource<Nothing>()
}
sealed class NetworkError {
    abstract val message: String?
    data class General(override val message: String = "Something went wrong. Please try again later.") : NetworkError()
    data class Unauthorized(override val message: String = "Not Authorized.") : NetworkError()
    data class Validation(override val message: String = "Data not valid.") : NetworkError()
    data class ServerError(override val message: String = "Server is busy. Please try again later.") : NetworkError()
    data class NetworkConnection(override val message: String = "Check your internet connection.") : NetworkError()
    data class ResponseIsNull(override val message: String? = null) : NetworkError()
}