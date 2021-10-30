package com.vishnu.testapplication.ui.login

import androidx.lifecycle.*
import com.vishnu.testapplication.data.LoginRequest
import com.vishnu.testapplication.domain.Result
import com.vishnu.testapplication.di.io
import com.vishnu.testapplication.domain.Event
import com.vishnu.testapplication.domain.LoginUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), KoinComponent {

    val userName = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val isLoginButtonEnable = MediatorLiveData<Boolean>().apply {
        fun validUserName() = userName.value.orEmpty().length > 3
        fun validPassword() = password.value.orEmpty().length > 3

        fun validate() = validUserName() && validPassword()
        addSource(userName) { this.value = validate() }
        addSource(password) { this.value = validate() }
    }

    /**
     * Calls Login asynchronously with CoroutineLiveData (Livedata inbuild with CoroutineContext & CoroutineScope)
     * the following is same as, updating a liveData with dispatcher.io and viewmodelScope.
     */
    private val loginEvent = MutableLiveData<Unit>()
    val login = loginEvent.switchMap {
        liveData(ioDispatcher) {
            emit(Result.Loading)
            val loginRequest = LoginRequest(username = userName.value.orEmpty(), password = password.value.orEmpty())
            val response = loginUseCase.login(loginRequest)
            emit(response)
        }
    }.map { Event(it) } // map to Single Event for one time EventObserver

    /**
     * Performs Launch time initialization or DeepLink parsing.
     */
    fun login() = viewModelScope.launch {
        loginEvent.value = Unit
    }
}