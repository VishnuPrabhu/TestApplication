package com.vishnu.testapplication.ui.welcome

import androidx.lifecycle.*
import com.vishnu.testapplication.domain.Event
import com.vishnu.testapplication.domain.OnBoardingCompleteUseCase
import com.vishnu.testapplication.domain.OnBoardingCompletedUseCase
import com.vishnu.testapplication.domain.util.WhileViewSubscribed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val onBoardingCompleteUseCase: OnBoardingCompleteUseCase
) : ViewModel() {

    private val _loginEvent = MutableLiveData<Unit>()
    val loginEvent: LiveData<Event<Unit>> = _loginEvent.map { Event(it) }

    /**
     * Performs Launch time initialization or DeepLink parsing.
     */
    fun setUserOnboarded() = viewModelScope.launch {
        onBoardingCompleteUseCase(true)
        _loginEvent.value = Unit
    }
}