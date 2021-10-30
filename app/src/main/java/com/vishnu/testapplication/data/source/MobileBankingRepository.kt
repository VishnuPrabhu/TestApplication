package com.vishnu.testapplication.data.source

import com.vishnu.testapplication.data.*
import com.vishnu.testapplication.domain.Result

interface MobileBankingRepository {
    suspend fun invalidate()
    suspend fun login(request: LoginRequest): Result<LoginResponse>
    suspend fun getAccountDetails(forceUpdate: Boolean): Result<AccountSummary>
    suspend fun getTransactions(forceUpdate: Boolean): Result<List<TransactionSummary>>
}