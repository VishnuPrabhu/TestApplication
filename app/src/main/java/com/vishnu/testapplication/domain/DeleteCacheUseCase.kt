package com.vishnu.testapplication.domain

import com.vishnu.testapplication.data.source.MobileBankingRepository

class DeleteCacheUseCase(
    private val repository: MobileBankingRepository
) {
    suspend operator fun invoke() {
        repository.invalidate()
    }
}