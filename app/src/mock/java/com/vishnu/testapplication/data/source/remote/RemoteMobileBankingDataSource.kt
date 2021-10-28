package com.vishnu.testapplication.data.source.remote

import com.vishnu.testapplication.data.LoginRequest
import com.vishnu.testapplication.data.LoginResponse
import com.vishnu.testapplication.data.source.MobileBankingRepository
import com.vishnu.testapplication.domain.Result

class RemoteMobileBankingDataSource(private val api: MobileBankingApi) : MobileBankingRepository {

    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = api.login(request)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun setApiToken(token: String) {
        throw IllegalStateException("This is used for storing locally in session. Should not be called for Remote Repository")
    }
}