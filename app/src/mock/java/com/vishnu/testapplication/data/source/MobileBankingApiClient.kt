package com.vishnu.testapplication.data.source

import com.vishnu.testapplication.data.BankingApiClient
import com.vishnu.testapplication.data.source.remote.MobileBankingApi

fun getApiClient(): MobileBankingApi {
    return BankingApiClient.createMock()
}