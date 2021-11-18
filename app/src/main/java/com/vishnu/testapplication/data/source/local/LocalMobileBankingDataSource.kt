package com.vishnu.testapplication.data.source.local

import com.vishnu.testapplication.data.*

enum class LocalCache {
    Login, Balance, Payees, Transactions, Transfer
}

class LocalMobileBankingDataSource {
    private val cache = HashMap<LocalCache, Any>()

    /**
     * Always return null for LoginCache since, we need to trigger each time when user logins.
     */
    fun login(): LoginResponse? {
        return null
    }

    fun accountDetails(): AccountSummary? {
        return cache[LocalCache.Balance] as? AccountSummary
    }

    fun cacheAccountDetails(value: AccountSummary?) {
        value?.let { cache[LocalCache.Balance] = it }
    }

    fun clear() {
        cache.clear()
    }

    fun transactionsList(): List<TransactionSummary>? {
        return cache[LocalCache.Transactions] as? List<TransactionSummary>
    }

    fun cacheTransactionsList(value: List<TransactionSummary>?) {
        value?.let { cache[LocalCache.Transactions] = it }
    }

    fun remove(item: LocalCache) {
        cache.remove(item)
    }

    fun clearTransactionsList() {
        cache.remove(LocalCache.Transactions)
    }

    fun payeesList(): List<Payee>? {
        return cache[LocalCache.Payees] as? List<Payee>
    }

    fun cachePayeesList(value: List<Payee>?) {
        value?.let { cache[LocalCache.Payees] = it }
    }

    fun clearPayeesList() {
        cache.remove(LocalCache.Payees)
    }


}