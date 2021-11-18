package com.vishnu.testapplication.domain

import com.vishnu.testapplication.data.Payee
import com.vishnu.testapplication.data.source.MobileBankingRepository

class GetPayeesUseCase(
    private val repository: MobileBankingRepository
) {
    suspend operator fun invoke(forceUpdate: Boolean = false): Result<List<Payee>> {
        return repository.getPayees(forceUpdate)
    }
}