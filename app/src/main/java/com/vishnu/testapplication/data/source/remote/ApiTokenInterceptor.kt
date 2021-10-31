package com.vishnu.testapplication.data.source.remote

import com.vishnu.testapplication.di.sessionHelper
import okhttp3.Interceptor
import okhttp3.Response

class ApiTokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        requestBuilder.header("Accept", "application/json")
        if (sessionHelper.getAuthToken().isNotEmpty()) {
            requestBuilder.header("Authorization", sessionHelper.getAuthToken())
        }
        return chain.proceed(requestBuilder.build())
    }
}