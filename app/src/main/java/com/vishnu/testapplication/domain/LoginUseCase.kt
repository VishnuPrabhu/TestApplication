package com.vishnu.testapplication.domain

import com.vishnu.testapplication.data.*
import com.vishnu.testapplication.data.source.MobileBankingRepository

class LoginUseCase(private val repository: MobileBankingRepository) {

    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        val result = repository.login(request)
        if (result.succeeded) {
            repository.setApiToken(result.data?.token.orEmpty())
        }
        return result
    }
}