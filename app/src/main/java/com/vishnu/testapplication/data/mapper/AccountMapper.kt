package com.vishnu.testapplication.data.mapper

import com.vishnu.testapplication.data.*
import com.vishnu.testapplication.domain.util.toCurrencyAmount
import com.vishnu.testapplication.domain.util.toNumber
import com.vishnu.testapplication.domain.util.toNumberString
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
        val amount = if (it.type == "transfer") {
            -it.amount.toNumber()
        } else {
            it.amount.toNumber()
        }.toNumberString()
        return TransactionSummary(
            it.id.orEmpty(),
            it.date.convertDate(YYYYMMDDHHMMSS, DD_MMM),
            it.description.orEmpty(),
            amount.toCurrencyAmount(it.currency.orEmpty())
        )
    }
}

object PayeesMapper {
    fun map(it: Payees): List<Payee> {
        return it.payees.orEmpty()
    }
}