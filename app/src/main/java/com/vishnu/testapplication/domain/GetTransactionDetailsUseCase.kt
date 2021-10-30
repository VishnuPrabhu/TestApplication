package com.vishnu.testapplication.domain

import com.vishnu.testapplication.data.AccountDetails
import com.vishnu.testapplication.data.TransactionSummary
import com.vishnu.testapplication.data.Transactions
import com.vishnu.testapplication.data.source.MobileBankingRepository

class GetTransactionDetailsUseCase(
    private val repository: MobileBankingRepository
) {
    suspend operator fun invoke(forceUpdate: Boolean = false): Result<List<TransactionSummary>> {
        return repository.getTransactions(forceUpdate)
    }
}