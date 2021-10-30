package com.vishnu.testapplication.data.source.mock

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.vishnu.testapplication.data.AccountDetails
import com.vishnu.testapplication.data.LoginRequest
import com.vishnu.testapplication.data.LoginResponse
import com.vishnu.testapplication.data.Transactions
import com.vishnu.testapplication.data.source.remote.MobileBankingApi
import org.koin.core.component.KoinComponent
import retrofit2.mock.BehaviorDelegate

class MockDataSource(val delegate: BehaviorDelegate<MobileBankingApi>, context: Context?) : MobileBankingApi, KoinComponent {

    val context = getKoin().get<Application>()

    override suspend fun login(request: LoginRequest): LoginResponse {
        val response = context.getMockResponse("mocks/login.json", LoginResponse::class.java)
        return delegate.returningResponse(response).login(request)
    }

    override suspend fun accountBalance(): AccountDetails {
        val response = context.getMockResponse("mocks/balance.json", AccountDetails::class.java)
        return delegate.returningResponse(response).accountBalance()
    }

    override suspend fun accountPayees() {
        TODO("Not yet implemented")
    }

    override suspend fun accountTransactions(): Transactions {
        val response = context.getMockResponse("mocks/transactions.json", Transactions::class.java)
        return delegate.returningResponse(response).accountTransactions()
    }

    override suspend fun transfer() {
        TODO("Not yet implemented")
    }

    private fun <T> Context.getMockResponse(path: String, type: Class<T>): T {
        return assets.open(path).use { inputStream ->
            val mockText = inputStream.bufferedReader().use { it.readText() }
            val mockResponse = Gson().fromJson(mockText, type)
            mockResponse
        }
    }

}