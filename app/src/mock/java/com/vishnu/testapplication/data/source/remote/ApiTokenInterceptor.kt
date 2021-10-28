package com.vishnu.testapplication.data.source.remote

import com.vishnu.testapplication.data.source.MobileBankingApiClient.LOGIN_AUTHORIZATION_TOKEN
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiTokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        requestBuilder.header("Accept", "application/json")
        if (LOGIN_AUTHORIZATION_TOKEN.isNotEmpty()) {
            requestBuilder.header("Authorization", LOGIN_AUTHORIZATION_TOKEN)
        }
        return chain.proceed(requestBuilder.build())
    }
}