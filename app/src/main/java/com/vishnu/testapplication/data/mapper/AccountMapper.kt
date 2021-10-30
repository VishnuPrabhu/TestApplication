package com.vishnu.testapplication.data.mapper

import com.vishnu.testapplication.data.AccountDetails
import com.vishnu.testapplication.data.AccountSummary
import com.vishnu.testapplication.data.Transaction
import com.vishnu.testapplication.data.TransactionSummary
import com.vishnu.testapplication.domain.util.toCurrencyAmount
import com.vishnu.testapplication.ui.util.DD_MMM
import com.vishnu.testapplication.ui.util.YYYYMMDDHHMMSS
import com.vishnu.testapplication.ui.util.convertDate

object AccountMapper {
    fun map(it: AccountDetails): AccountSummary {
        return AccountSummary(
            "Ocbc Savings Account",
            "1234567890",
            it.balance?.toString().orEmpty()
        )
    }
}

object TransactionsMapper {
    fun map(it: Transaction): TransactionSummary {
        return TransactionSummary(
            it.id.orEmpty(),
            it.date.convertDate(YYYYMMDDHHMMSS, DD_MMM),
            it.description.orEmpty(),
            it.amount.toCurrencyAmount(it.currency.orEmpty())
        )
    }
}