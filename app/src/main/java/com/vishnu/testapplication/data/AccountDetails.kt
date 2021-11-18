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
    @SerializedName("from", alternate = ["to"])
    val details: Details?,
    val id: String?,
    val type: String?
)

@Keep
data class Details(
    val accountHolderName: String,
    val accountNo: String
)