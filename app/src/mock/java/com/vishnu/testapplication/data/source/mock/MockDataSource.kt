package com.vishnu.testapplication.data.source.mock

import android.content.Context
import com.vishnu.testapplication.data.LoginRequest
import com.vishnu.testapplication.data.LoginResponse
import com.vishnu.testapplication.data.source.remote.MobileBankingApi
import retrofit2.mock.BehaviorDelegate

class MockDataSource(delegate: BehaviorDelegate<MobileBankingApi>, context: Context?) : MobileBankingApi {

    override suspend fun login(request: LoginRequest): LoginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun accountBalance() {
        TODO("Not yet implemented")
    }

    override suspend fun accountPayees() {
        TODO("Not yet implemented")
    }

    override suspend fun accountTransactions() {
        TODO("Not yet implemented")
    }

    override suspend fun transfer() {
        TODO("Not yet implemented")
    }

}