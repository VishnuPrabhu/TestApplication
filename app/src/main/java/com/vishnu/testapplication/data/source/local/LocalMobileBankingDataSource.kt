package com.vishnu.testapplication.data.source.local

import com.vishnu.testapplication.data.LoginResponse

enum class LocalCache {
    Login, Balance, Payees, Transactions, Transfer
}

class LocalMobileBankingDataSource {
    val cache = HashMap<LocalCache, Any>()

    /**
     * Always return null for LoginCache since, we need to trigger each time when user logins.
     */
    fun login(): LoginResponse? {
        return null
    }
}