package com.vishnu.testapplication.ui.login

import androidx.lifecycle.*
import com.vishnu.testapplication.domain.Event
import com.vishnu.testapplication.domain.OnBoardingCompletedUseCase
import com.vishnu.testapplication.domain.util.WhileViewSubscribed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LaunchViewModel(
    private val onBoardingCompletedUseCase: OnBoardingCompletedUseCase,
) : ViewModel() {

    private val _launchApplication = MutableLiveData<Event<Unit>>()
    val launchApplication: LiveData<Event<Unit>> = _launchApplication

    private val _isCustomerOnboarded: StateFlow<Boolean> = flow {
        val isOnboarded = onBoardingCompletedUseCase()
        emit(isOnboarded)
    }.stateIn(viewModelScope, WhileViewSubscribed, initialValue = false)
    val isCustomerOnboarded: LiveData<Event<Boolean>> = _isCustomerOnboarded.asLiveData()
                                                                            .map { Event(it) }

    /**
     * Performs Launch time initialization or DeepLink parsing.
     */
    fun launch() = viewModelScope.launch {
        //delay(2000)
        _launchApplication.value = Event(Unit)
    }
}