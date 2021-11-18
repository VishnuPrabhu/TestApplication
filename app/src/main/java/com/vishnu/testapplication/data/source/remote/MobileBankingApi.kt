package com.vishnu.testapplication.data.source.remote

import com.vishnu.testapplication.data.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MobileBankingApi {

    @POST("/authenticate/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("/account/balances")
    suspend fun accountBalance(): AccountDetails

    @GET("/account/payees")
    suspend fun accountPayees(): Payees

    @GET("/account/transactions")
    suspend fun accountTransactions(): Transactions

    @POST("/transfer")
    suspend fun transfer(@Body request: TransferRequest): TransferResponse
}