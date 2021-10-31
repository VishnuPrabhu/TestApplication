package com.vishnu.testapplication.data

import com.vishnu.testapplication.BuildConfig
import com.vishnu.testapplication.data.source.mock.MockDataSource
import com.vishnu.testapplication.data.source.remote.ApiTokenInterceptor
import com.vishnu.testapplication.data.source.remote.MobileBankingApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

val BASE_URL = "http://localhost:8080"

object BankingApiClient {

    internal fun create(): MobileBankingApi {
        // OkHttpClient to add interceptors
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(ApiTokenInterceptor())
        if (BuildConfig.BUILD_TYPE == "debug") {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.networkInterceptors().add(httpLoggingInterceptor)
        }
        val client = builder.build()
        // Retrofit Builder
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(MobileBankingApi::class.java)
    }

    internal fun createMock(): MobileBankingApi {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        val behavior = NetworkBehavior.create()
        val mockRetrofit = MockRetrofit.Builder(retrofit).networkBehavior(behavior).build()
        val delegate: BehaviorDelegate<MobileBankingApi> = mockRetrofit.create(MobileBankingApi::class.java)
        return MockDataSource(delegate, null)
    }
}