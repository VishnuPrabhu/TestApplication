package com.vishnu.testapplication.domain

import com.vishnu.testapplication.data.AccountDetails
import com.vishnu.testapplication.data.AccountSummary
import com.vishnu.testapplication.data.source.MobileBankingRepository
import com.vishnu.testapplication.domain.util.toCurrencyAmount

class GetAccountDetailsUseCase(
    private val repository: MobileBankingRepository
) {
    suspend operator fun invoke(forceUpdate: Boolean = false): Result<AccountSummary> {
        return repository.getAccountDetails(forceUpdate).let {
            if (it is Result.Success) {
                it.data.balance = it.data.balance.toCurrencyAmount()
            }
            it
        }
    }
}