package com.mousa.core.domain.util


sealed class BaseState<out T> {
    data object Loading : BaseState<Nothing>()
    data class Success<T>(val data: T) : BaseState<T>()
    data class Failure(val errorMessage: String?) : BaseState<Nothing>()
}