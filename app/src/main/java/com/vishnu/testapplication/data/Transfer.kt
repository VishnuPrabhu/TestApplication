package com.vishnu.testapplication.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TransferRequest(
    val amount: Int,
    val date: String,
    val description: String,
    val recipientAccountNo: String
)

@Keep
data class TransferResponse(
    @SerializedName("data") val response: Data,
    val status: String
)

data class Data(
    val amount: Int,
    val date: String,
    val description: String,
    val id: String,
    val recipientAccountNo: String
)