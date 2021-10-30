package com.vishnu.testapplication.domain

import com.vishnu.testapplication.data.TransferRequest
import com.vishnu.testapplication.data.TransferResponse
import com.vishnu.testapplication.data.source.MobileBankingRepository

class TransferAmountUseCase(
    private val repository: MobileBankingRepository
) {
    suspend operator fun invoke(request: TransferRequest): Result<TransferResponse> {
        return repository.transfer(request)
    }
}