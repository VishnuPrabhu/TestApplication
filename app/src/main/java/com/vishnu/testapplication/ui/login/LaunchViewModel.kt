package com.vishnu.testapplication.ui.login

import androidx.lifecycle.*
import com.vishnu.testapplication.domain.Event
import com.vishnu.testapplication.domain.OnBoardingCompletedUseCase
import com.vishnu.testapplication.domain.util.WhileViewSubscribed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LaunchViewModel(
    private val onBoardingCompletedUseCase: OnBoardingCompletedUseCase,
) : ViewModel() {

    private val _launchApplication = MutableLiveData<Event<Unit>>()
    val launchApplication: LiveData<Event<Unit>> = _launchApplication

    private val _isCustomerOnboarded: Flow<Boolean> = flow {
        val isOnboarded = onBoardingCompletedUseCase()
        emit(isOnboarded)
    }
    val isCustomerOnboarded: LiveData<Boolean> = _isCustomerOnboarded.asLiveData()

    /**
     * Performs Launch time initialization or DeepLink parsing.
     */
    fun launch() = viewModelScope.launch {
        _launchApplication.value = Event(Unit)
    }
}