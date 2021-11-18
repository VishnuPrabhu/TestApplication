package com.vishnu.testapplication.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
data class AccountSummary(
    var accountName: String = "",
    var accountNumber: String = "",
    var balance: String = ""
)

@Keep
data class TransactionSummary(
    val id: String = "",
    var date: String = "",
    val amount: String = "",
    val payeeName: String = "",
    val payeeAccountNumber: String = "",
    val description: String = "",
)

@Parcelize
@Keep
data class Payee(
    val id: String,
    val accountHolderName: String,
    val accountNo: String
) : Parcelable