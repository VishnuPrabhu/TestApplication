package com.vishnu.testapplication.ui.home

import androidx.lifecycle.*
import com.vishnu.testapplication.data.AccountSummary
import com.vishnu.testapplication.data.Transaction
import com.vishnu.testapplication.data.TransactionSummary
import com.vishnu.testapplication.data.Transactions
import com.vishnu.testapplication.di.main
import com.vishnu.testapplication.di.sessionHelper
import com.vishnu.testapplication.domain.DeleteCacheUseCase
import com.vishnu.testapplication.domain.GetAccountDetailsUseCase
import com.vishnu.testapplication.domain.GetTransactionDetailsUseCase
import com.vishnu.testapplication.domain.Result
import com.vishnu.testapplication.domain.util.toCurrencyAmount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val getTransactionDetailsUseCase: GetTransactionDetailsUseCase,
    private val deleteCacheUseCase: DeleteCacheUseCase
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val _loadingBalance = MutableLiveData<Boolean>(false)
    val loadingBalance: LiveData<Boolean> = _loadingBalance

    private val _loadingTransactions = MutableLiveData<Boolean>(false)
    val loadingTransactions: LiveData<Boolean> = _loadingTransactions

    private val _account = MutableLiveData<AccountSummary>()
    val account: LiveData<AccountSummary> = _account

    private val _transactions = MutableLiveData<List<TransactionSummary>>()
    val transactions: LiveData<List<TransactionSummary>> = _transactions

    init {
        refresh()
    }

    fun refresh() {
        fetchAccountDetails(forceRefresh = true)
    }

    private fun fetchAccountDetails(forceRefresh: Boolean = false) = viewModelScope.launch(Dispatchers.main) {
        if (!forceRefresh || _loading.value == true) {
            return@launch
        }
        _loading.value = true

        async {
            _loadingBalance.value = true
            if (forceRefresh) {
                deleteCacheUseCase()
            }
            launch {
                getAccountDetailsUseCase().let { result: Result<AccountSummary> ->
                    if (result is Result.Success) {
                        onAccountBalanceReceived(result.data)
                    } else {
                        onError((result as Result.Error).exception.message)
                    }
                    _loadingBalance.value = false
                }
            }
            launch {
                getTransactionDetailsUseCase().let { result: Result<List<TransactionSummary>> ->
                    _loadingTransactions.value = true
                    if (result is Result.Success) {
                        onTransactionsReceived(result.data)
                    } else {
                        onTransactionsError((result as Result.Error).exception.message)
                    }
                    _loadingTransactions.value = false
                }
            }
        }.await()
        _loading.value = false
    }

    private fun onAccountBalanceReceived(data: AccountSummary) {
        _account.value = data
    }

    private fun onError(error: String?) {
        val error = error ?: "Unable to Fetch Balance"
        _account.value = AccountSummary(accountName = error, balance = "0".toCurrencyAmount())
    }

    private fun onTransactionsReceived(data: List<TransactionSummary>) {
        _transactions.value = data
    }

    private fun onTransactionsError(result: String?) {
        _transactions.value = emptyList()
    }
}