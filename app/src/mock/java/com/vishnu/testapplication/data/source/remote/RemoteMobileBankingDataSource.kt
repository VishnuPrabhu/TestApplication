package com.vishnu.testapplication.data.source.remote

import com.vishnu.testapplication.data.AccountDetails
import com.vishnu.testapplication.data.LoginRequest
import com.vishnu.testapplication.data.LoginResponse
import com.vishnu.testapplication.data.Transactions
import com.vishnu.testapplication.data.source.MobileBankingRepository
import com.vishnu.testapplication.domain.Result

class RemoteMobileBankingDataSource(private val api: MobileBankingApi) {

    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = api.login(request)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun accountDetails(): Result<AccountDetails> {
        return try {
            val response = api.accountBalance()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun transactionsList(): Result<Transactions> {
        return try {
            val response = api.accountTransactions()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}