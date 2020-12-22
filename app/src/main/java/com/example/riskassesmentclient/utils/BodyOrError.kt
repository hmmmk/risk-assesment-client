package com.example.riskassesmentclient.utils

import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response


fun <T> Call<T>.bodyOrError(): T {
    return this.execute().bodyOrError()
}

fun <T> Response<T>.bodyOrError(): T {
    if (this.isSuccessful) {
        return this.body()!!
    }

    throw HttpException(this)
}

fun <T> Call<T>.isSuccessfulOrError(): Boolean {
    return this.execute().isSuccessfulOrError()
}

fun <T> Response<T>.isSuccessfulOrError(): Boolean {
    if (this.isSuccessful) {
        return true
    }

    throw HttpException(this)
}