package com.vishnu.testapplication.data.source.local

import com.vishnu.testapplication.di.io
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionHelper(private val dao: SessionDao) {

    suspend fun getApiToken(): String = withContext(Dispatchers.io) {
        dao.get("API_TOKEN").orEmpty()
    }

    suspend fun setApiToken(token: String) = withContext(Dispatchers.io) {
        dao.put(Session("API_TOKEN", token))
    }

    suspend fun clearSession() = withContext(Dispatchers.io) {
        dao.clearAll()
    }
}