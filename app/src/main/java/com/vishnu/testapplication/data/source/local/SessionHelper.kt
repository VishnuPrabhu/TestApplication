package com.vishnu.testapplication.data.source.local

import com.vishnu.testapplication.di.io
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

class SessionHelper(private val dao: SessionDao) {

    private val refreshAccountDetails = MutableStateFlow<Boolean>(false)

    private var _loginAuthToken = ""

    fun getAuthToken(): String = _loginAuthToken

    suspend fun getApiToken(): String = withContext(Dispatchers.io) {
        dao.get("API_TOKEN").orEmpty()
    }

    suspend fun setApiToken(token: String) = withContext(Dispatchers.io) {
        _loginAuthToken = token
        dao.put(Session("API_TOKEN", token))
    }

    suspend fun clearSession() = withContext(Dispatchers.io) {
        dao.clearAll()
    }

    fun refresh() {
        refreshAccountDetails.value = true
    }

    fun refreshCompleted() {
        refreshAccountDetails.value = false
    }

    suspend fun registerCallback(callback: () -> Unit) {
        refreshAccountDetails.collect { refresh ->
            if (refresh) {
                callback()
                refreshCompleted()
            }
        }
    }
}