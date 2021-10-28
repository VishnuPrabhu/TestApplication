package com.vishnu.testapplication.data.source

import com.vishnu.testapplication.data.LoginRequest
import com.vishnu.testapplication.data.LoginResponse
import com.vishnu.testapplication.domain.Result
import com.vishnu.testapplication.data.source.local.LocalMobileBankingDataSource
import com.vishnu.testapplication.data.source.remote.RemoteMobileBankingDataSource
import com.vishnu.testapplication.di.sessionHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultDataSource(
    private val localDataSource: LocalMobileBankingDataSource,
    private val remoteDataSource: RemoteMobileBankingDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MobileBankingRepository {

    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return withContext(ioDispatcher) {
            remoteDataSource.login(request)
        }
    }

    override suspend fun setApiToken(token: String) {
        sessionHelper.setApiToken(token)
    }

    override suspend fun invalidate() {
        localDataSource.cache.clear()
    }

}