package com.vishnu.testapplication.data

import androidx.annotation.Keep

@Keep
data class AccountSummary(
    var accountName: String = "",
    var accountNumber: String = "",
    var balance: String = ""
)

@Keep
data class TransactionSummary(
    val id: String = "",
    val date: String = "",
    val description: String = "",
    val amount: String = "",
)