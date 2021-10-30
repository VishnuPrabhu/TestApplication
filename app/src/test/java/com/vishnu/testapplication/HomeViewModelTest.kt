package com.vishnu.testapplication

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.vishnu.testapplication.base.AndroidUnitTest
import com.vishnu.testapplication.di.homeModule
import com.vishnu.testapplication.di.repositoryModule
import com.vishnu.testapplication.domain.GetAccountDetailsUseCase
import com.vishnu.testapplication.domain.GetTransactionDetailsUseCase
import com.vishnu.testapplication.domain.data
import com.vishnu.testapplication.domain.util.digits
import com.vishnu.testapplication.base.getOrAwaitValue
import com.vishnu.testapplication.data.AccountDetails
import com.vishnu.testapplication.data.Payees
import com.vishnu.testapplication.data.Transactions
import com.vishnu.testapplication.data.mapper.AccountMapper
import com.vishnu.testapplication.data.mapper.TransactionsMapper
import com.vishnu.testapplication.data.source.MobileBankingRepository
import com.vishnu.testapplication.data.source.mock.getMockResponse
import com.vishnu.testapplication.domain.Result
import com.vishnu.testapplication.ui.home.HomeViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.test.mock.declareMock
import kotlin.test.assertEquals

class HomeViewModelTest : AndroidUnitTest() {

    @Test
    fun testBalanceUseCase() = runBlocking {
        loadKoinModules(listOf(repositoryModule, homeModule))

        val mock = declareMock<MobileBankingRepository>()
        given(mock.getAccountDetails(any())).willAnswer {
            val remoteResponse = context.getMockResponse("mocks/balance.json", AccountDetails::class.java)
            val mockResponse = AccountMapper.map(remoteResponse)
            Result.Success(mockResponse)
        }

        val useCase: GetAccountDetailsUseCase by inject()
        val result = useCase.invoke()
        assertEquals("100000.00", result.data?.balance?.digits().orEmpty())
    }


    @Test
    fun testTransactionsUseCase() = runBlocking {
        loadKoinModules(listOf(repositoryModule, homeModule))

        val mock = declareMock<MobileBankingRepository>()
        given(mock.getTransactions(any())).willAnswer {
            val remoteResponse = context.getMockResponse("mocks/transactions.json", Transactions::class.java)
            val mockResponse = remoteResponse.transactions.map { TransactionsMapper.map(it) }
            Result.Success(mockResponse)
        }

        val useCase: GetTransactionDetailsUseCase by inject()
        val result = useCase.invoke()
        assertEquals(5, result.data.orEmpty().size)
        // receive
        assertEquals("SGD 18.50", result.data?.firstOrNull()?.amount)
        // transfer
        assertEquals("SGD -1,800.00", result.data?.get(2)?.amount)
    }


    @Test
    fun testHomeViewModelLoading() = runBlocking {
        loadKoinModules(listOf(repositoryModule, homeModule))

        val viewModel: HomeViewModel by inject()
        viewModel.refresh()

        // when
        val waitTillLoadingFinished = viewModel.loading.getOrAwaitValue(2)

        // assert
        //assertEquals("SGD 100,000.00", viewModel.account.value?.balance.orEmpty())
        assertEquals("12 Sep", viewModel.transactions.value?.firstOrNull()?.date.orEmpty())
    }
}