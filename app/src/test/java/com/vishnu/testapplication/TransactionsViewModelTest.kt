package com.vishnu.testapplication

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.vishnu.testapplication.base.AndroidUnitTest
import com.vishnu.testapplication.base.getOrAwaitValue
import com.vishnu.testapplication.data.*
import com.vishnu.testapplication.data.mapper.PayeesMapper
import com.vishnu.testapplication.data.source.MobileBankingRepository
import com.vishnu.testapplication.data.source.mock.getMockResponse
import com.vishnu.testapplication.di.repositoryModule
import com.vishnu.testapplication.di.transferModule
import com.vishnu.testapplication.domain.*
import com.vishnu.testapplication.ui.home.TransferViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.test.mock.declareMock
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TransactionsViewModelTest : AndroidUnitTest() {

    @Test
    fun testGetPayeesUseCase() = runBlocking {
        loadKoinModules(listOf(repositoryModule, transferModule))

        val mock = declareMock<MobileBankingRepository>()
        given(mock.getPayees(any())).willAnswer {
            val remoteResponse = context.getMockResponse("mocks/payees.json", Payees::class.java)
            val mockResponse = PayeesMapper.map(remoteResponse)
            Result.Success(mockResponse)
        }

        val useCase: GetPayeesUseCase by inject()
        val result = useCase.invoke()
        assertEquals("Jason", result.data?.firstOrNull()?.accountHolderName.orEmpty())
        assertEquals("8013-777-3232", result.data?.firstOrNull()?.accountNo.orEmpty())
    }

    @Test
    fun testTransferUseCase() = runBlocking {
        loadKoinModules(listOf(repositoryModule, transferModule))
        val mock = declareMock<MobileBankingRepository>()
        given(mock.transfer(any())).willAnswer {
            val request = it.arguments[0] as TransferRequest
            Result.Success(
                TransferResponse(
                    response = Data(request.amount, request.date, request.description, "12345", request.recipientAccountNo),
                    status = "Success"
                )
            )
        }

        val useCase: TransferAmountUseCase by inject()
        val request = TransferRequest(1, "30 Oct", "test send", "80137773232")
        val result = useCase.invoke(request)
        assertEquals("test send", result.data?.response?.description.orEmpty())
    }


    @Test
    fun testTransferViewModelLiveData() = runBlocking {
        loadKoinModules(listOf(repositoryModule, transferModule))
        val mockInvalidateUseCase = declareMock<InvalidateSessionUseCase>()
        val mock = declareMock<MobileBankingRepository>()
        given(mock.transfer(any())).willAnswer {
            val request = it.arguments[0] as TransferRequest
            Result.Success(
                TransferResponse(
                    response = Data(request.amount, request.date, request.description, "12345", request.recipientAccountNo),
                    status = "Success"
                )
            )
        }

        // when no data is set
        val viewModel: TransferViewModel by inject()
        viewModel.setUpValidations()

        // set the data
        viewModel.transferPayee.value = Payee("1", "Jason", "80137773232")
        viewModel.transferDate.value = "30 Oct"
        viewModel.transferComments.value = "test send"
        viewModel.transferAmount.value = "1"

        val isSubmitEnabled = viewModel.isSubmitButtonEnabled.getOrAwaitValue()
        assertTrue { isSubmitEnabled }

        // call api
        viewModel.transferAmount()
        val result = viewModel.transferSuccess.getOrAwaitValue()
        assertEquals(result.peekContent(), "Trasaction Reference Number 12345 is Success. \n" +
                "AccountNumber : 80137773232 \n" +
                "Date          : 30 Oct \n" +
                "Description   : test send \n" +
                "Amount        : 1")
    }



}