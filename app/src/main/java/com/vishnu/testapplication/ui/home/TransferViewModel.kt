package com.vishnu.testapplication.ui.home

import androidx.lifecycle.*
import com.vishnu.testapplication.data.Payee
import com.vishnu.testapplication.data.TransferRequest
import com.vishnu.testapplication.di.io
import com.vishnu.testapplication.di.main
import com.vishnu.testapplication.domain.*
import com.vishnu.testapplication.domain.util.isNotNullOrEmpty
import com.vishnu.testapplication.domain.util.toAmount
import com.vishnu.testapplication.domain.util.toNumber
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransferViewModel(
    private val getPayeesUseCase: GetPayeesUseCase,
    private val transferAmountUseCase: TransferAmountUseCase,
    private val invalidateSessionUseCase: InvalidateSessionUseCase
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val _payees = MutableLiveData<Event<List<Payee>>>()
    val payees: LiveData<Event<List<Payee>>> = _payees

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private val _transferSuccess = MutableLiveData<Event<String>>()
    val transferSuccess: LiveData<Event<String>> = _transferSuccess

    val transferPayee = MutableLiveData<Payee>()
    val transferDate = MutableLiveData<String>()
    val transferComments = MutableLiveData<String>()
    val transferAmount = MutableLiveData<String>()

    val isSubmitButtonEnabled = MediatorLiveData<Boolean>()

    init {
        viewModelScope.launch {
            _loading.value = true
            getPayeesUseCase().let {
                if (it is Result.Success) {
                    _payees.value = Event(it.data)
                } else {
                    _error.value = Event("Payees failed")
                }
            }
            _loading.value = false
        }
    }

    fun setUpValidations() {
        isSubmitButtonEnabled.apply {
            addSource(transferPayee) {
                validate()
            }
            addSource(transferDate) {
                validate()
            }
            addSource(transferAmount) {
                validate()
            }
        }
    }

    fun validate() {
        isSubmitButtonEnabled.value = transferPayee.value?.accountHolderName.isNotNullOrEmpty() &&
                                      transferDate.value.isNotNullOrEmpty() &&
                                      (transferAmount.value.isNotNullOrEmpty() && transferAmount.value.orEmpty().toNumber() > 0)
    }

    fun transferAmount() = viewModelScope.launch(Dispatchers.main) {
        val request = TransferRequest(
            recipientAccountNo = transferPayee.value?.accountNo.orEmpty(),
            date = transferDate.value.orEmpty(),
            description = transferComments.value.orEmpty().ifEmpty { "Transfer" },
            amount = transferAmount.value.orEmpty().toNumber().toInt()
        )
        _loading.value = true
        transferAmountUseCase(request).let {
            if (it is Result.Success) {
                _transferSuccess.value = Event("Trasaction Reference Number ${it.data.response.id} is Success. \n" +
                        "AccountNumber : ${it.data.response.recipientAccountNo} \n" +
                        "Date          : ${it.data.response.date} \n" +
                        "Description   : ${it.data.response.description} \n" +
                        "Amount        : ${it.data.response.amount}")
                invalidateSessionUseCase()
            } else {
                _error.value = Event("There was an error during this transactions. Please try again")
            }
        }
        _loading.value = false
    }
}