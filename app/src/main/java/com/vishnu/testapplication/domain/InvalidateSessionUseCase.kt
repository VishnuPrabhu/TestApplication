package com.vishnu.testapplication.domain

import com.vishnu.testapplication.data.TransferRequest
import com.vishnu.testapplication.data.TransferResponse
import com.vishnu.testapplication.data.source.MobileBankingRepository
import com.vishnu.testapplication.di.sessionHelper

class InvalidateSessionUseCase(
    private val repository: MobileBankingRepository
) {
    suspend operator fun invoke() {
        sessionHelper.refresh()
        repository.invalidate()
    }
}