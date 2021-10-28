package com.vishnu.testapplication.data.source

import com.vishnu.testapplication.data.LoginRequest
import com.vishnu.testapplication.data.LoginResponse
import com.vishnu.testapplication.domain.Result

interface MobileBankingRepository {
    suspend fun login(request: LoginRequest): Result<LoginResponse>
    suspend fun setApiToken(token: String)
    suspend fun invalidate()
}