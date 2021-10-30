package com.vishnu.testapplication.data.source.remote

import com.vishnu.testapplication.data.AccountDetails
import com.vishnu.testapplication.data.LoginRequest
import com.vishnu.testapplication.data.LoginResponse
import com.vishnu.testapplication.data.Transactions
import retrofit2.http.Body
import retrofit2.http.POST

interface MobileBankingApi {

    @POST("/authenticate/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/account/balances")
    suspend fun accountBalance(): AccountDetails

    @POST("/account/payees")
    suspend fun accountPayees()

    @POST("/account/transactions")
    suspend fun accountTransactions(): Transactions

    @POST("/transfer")
    suspend fun transfer()
}