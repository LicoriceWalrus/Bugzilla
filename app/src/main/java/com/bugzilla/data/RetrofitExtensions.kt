package com.bugzilla.data

import retrofit2.Response

fun <T> Response<T>.getOrThrow(): T {
    val body = body()
    if (isSuccessful && body != null) {
        return body
    } else {
        when (this.code()) {
            404 -> throw NotFoundException(this.message().orEmpty())
            else -> throw DataException(this.message().orEmpty())
        }
    }
}