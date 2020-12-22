package com.example.riskassesmentclient.utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val token =
            context.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("token", "")!!

        if (token.isNotEmpty())
            requestBuilder.addHeader("Authorization", token)

        return chain.proceed(requestBuilder.build())
    }
}