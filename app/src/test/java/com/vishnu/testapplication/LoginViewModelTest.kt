package com.vishnu.testapplication

import com.nhaarman.mockito_kotlin.any
import com.vishnu.testapplication.base.AndroidUnitTest
import com.vishnu.testapplication.base.getOrAwaitValue
import com.vishnu.testapplication.data.source.local.SessionHelper
import com.vishnu.testapplication.di.loginModule
import com.vishnu.testapplication.di.repositoryModule
import com.vishnu.testapplication.domain.LoginUseCase
import com.vishnu.testapplication.domain.Result
import com.vishnu.testapplication.domain.data
import com.vishnu.testapplication.ui.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.test.mock.declare
import org.mockito.Mockito.mock
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class LoginViewModelTest : AndroidUnitTest(), KoinComponent {

    @Test
    fun testLogin() = runBlocking {
        loadKoinModules(listOf(repositoryModule, loginModule))
        declare {
            mock(SessionHelper::class.java)
        }
        val loginViewModel: LoginViewModel by inject()
        loginViewModel.login()

        val result = loginViewModel.login.getOrAwaitValue(2)
        assertTrue { result.peekContent() is Result.Success }
    }

    @Test
    fun testLoginRepository() = runBlocking {
        loadKoinModules(listOf(repositoryModule, loginModule))
        declare {
            mock(SessionHelper::class.java)
        }

        val loginUseCase: LoginUseCase by inject()
        val response = loginUseCase(any())
        assertTrue { response is Result.Success }
        val data = response.data
        assertTrue { data?.token == "1234567890qwertyuiop" }
    }

    @Test
    fun testLoginLiveData() = runBlocking {
        loadKoinModules(listOf(repositoryModule, loginModule))
        declare {
            mock(SessionHelper::class.java)
        }
        val loginViewModel: LoginViewModel by inject()

        loginViewModel.userName.value = "ocbc"
        loginViewModel.password.value = ""

        var enabled = loginViewModel.isLoginButtonEnable.getOrAwaitValue()
        assertFalse { enabled }

        loginViewModel.password.value = "123456"
        enabled = loginViewModel.isLoginButtonEnable.getOrAwaitValue()
        assertTrue { enabled }
    }

}