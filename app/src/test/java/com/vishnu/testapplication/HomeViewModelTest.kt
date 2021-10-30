package com.vishnu.testapplication

import com.vishnu.testapplication.base.AndroidUnitTest
import com.vishnu.testapplication.di.homeModule
import com.vishnu.testapplication.di.repositoryModule
import com.vishnu.testapplication.domain.GetAccountDetailsUseCase
import com.vishnu.testapplication.domain.GetTransactionDetailsUseCase
import com.vishnu.testapplication.domain.data
import com.vishnu.testapplication.domain.util.digits
import com.vishnu.testapplication.base.getOrAwaitValue
import com.vishnu.testapplication.ui.home.HomeViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import kotlin.test.assertEquals

class HomeViewModelTest : AndroidUnitTest() {

    @Test
    fun testBalanceUseCase() = runBlocking {
        loadKoinModules(listOf(repositoryModule, homeModule))

        val useCase: GetAccountDetailsUseCase by inject()
        val result = useCase.invoke()
        assertEquals("100000.00", result.data?.balance?.digits().orEmpty())
    }


    @Test
    fun testTransactionsUseCase() = runBlocking {
        loadKoinModules(listOf(repositoryModule, homeModule))

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