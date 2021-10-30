package com.vishnu.testapplication.data.source

import com.vishnu.testapplication.data.*
import com.vishnu.testapplication.data.mapper.AccountMapper
import com.vishnu.testapplication.data.mapper.TransactionsMapper
import com.vishnu.testapplication.domain.Result
import com.vishnu.testapplication.data.source.local.LocalMobileBankingDataSource
import com.vishnu.testapplication.data.source.remote.RemoteMobileBankingDataSource
import com.vishnu.testapplication.di.io
import com.vishnu.testapplication.domain.data
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultDataSource(
    private val localDataSource: LocalMobileBankingDataSource,
    private val remoteDataSource: RemoteMobileBankingDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.io
) : MobileBankingRepository {

    override suspend fun invalidate() {
        localDataSource.clear()
    }

    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return withContext(ioDispatcher) {
            remoteDataSource.login(request)
        }
    }

    override suspend fun getAccountDetails(forceUpdate: Boolean): Result<AccountSummary> {
        val cacheResponse = localDataSource.accountDetails()

        return if (cacheResponse == null || forceUpdate) {
            withContext(ioDispatcher) {
                val balance = remoteDataSource.accountDetails()
                when(balance) {
                    is Result.Success -> {
                        val summary = AccountMapper.map(balance.data).also {
                            localDataSource.cacheAccountDetails(it)
                        }
                        Result.Success(summary)
                    }
                    is Result.Loading -> Result.Loading
                    is Result.Error -> Result.Error(balance.exception)
                }
            }
        } else {
            Result.Success(cacheResponse)
        }
    }

    override suspend fun getTransactions(forceUpdate: Boolean): Result<List<TransactionSummary>> {
        val cacheResponse = localDataSource.transactionsList()

        return if (cacheResponse == null || forceUpdate) {
            withContext(ioDispatcher) {
                val transactions = remoteDataSource.transactionsList()
                when(transactions) {
                    is Result.Success -> {
                        val transactionsSummary = transactions.data.transactions.map {
                            TransactionsMapper.map(it)
                        }.also {
                            localDataSource.cacheTransactionsList(it)
                        }
                        Result.Success(transactionsSummary)
                    }
                    is Result.Loading -> Result.Loading
                    is Result.Error -> {
                        localDataSource.clearTransactionsList()
                        Result.Error(transactions.exception)
                    }
                }
            }
        } else {
            Result.Success(cacheResponse)
        }
    }

}