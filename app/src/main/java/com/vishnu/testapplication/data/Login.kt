package com.vishnu.testapplication.data

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity

@Keep
data class LoginRequest(
    val password: String,
    val username: String
)

@Keep
data class LoginResponse(
    val status: String,
    val token: String
)