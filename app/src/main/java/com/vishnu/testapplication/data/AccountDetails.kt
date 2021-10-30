package com.vishnu.testapplication.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AccountDetails(
    val balance: Long?,
    val status: String?
)

@Keep
data class Transactions(
    @SerializedName("data") val transactions: List<Transaction>,
    val status: String
)

@Keep
data class Transaction(
    val amount: String?,
    val currency: String?,
    val date: String?,
    val description: String?,
    val from: From?,
    val id: String?,
    val to: To?,
    val type: String?
)

@Keep
data class From(
    val accountHolderName: String,
    val accountNo: String
)

@Keep
data class To(
    val accountHolderName: String,
    val accountNo: String
)