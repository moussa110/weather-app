package com.mousa.core.domain.mapper

import com.mousa.core.domain.util.BaseState
import com.mousa.core.domain.util.Resource

fun <T> Resource<T>.toBaseState(): BaseState<T> {
    return when (this) {
        is Resource.Success -> BaseState.Success(this.value)
        is Resource.Failure -> BaseState.Failure(
            this.networkError?.message
        )
    }
}
