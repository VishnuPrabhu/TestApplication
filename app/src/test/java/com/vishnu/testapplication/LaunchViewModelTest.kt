package com.vishnu.testapplication

import android.content.Context
import com.vishnu.testapplication.base.AndroidUnitTest
import com.vishnu.testapplication.domain.OnBoardingCompleteUseCase
import com.vishnu.testapplication.domain.OnBoardingCompletedUseCase
import com.vishnu.testapplication.base.getOrAwaitValue
import com.vishnu.testapplication.ui.login.LaunchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class LaunchViewModelTest : AndroidUnitTest() {

    @Test
    fun testLaunchFirstTime() = runBlocking {
        val mockUseCase = mock(OnBoardingCompletedUseCase::class.java)
        given(mockUseCase.invoke()).willReturn(false)
        val viewModel = LaunchViewModel(mockUseCase)

        viewModel.launch()
        val result = viewModel.isCustomerOnboarded.getOrAwaitValue()
        assertFalse { result }
    }

    @Test
    fun testLaunchSecondTime() = runBlocking {
        val mockUseCase = mock(OnBoardingCompletedUseCase::class.java)
        given(mockUseCase.invoke()).willReturn(true)
        val viewModel = LaunchViewModel(mockUseCase)

        viewModel.launch()
        val result = viewModel.isCustomerOnboarded.getOrAwaitValue()
        assertTrue { result }
    }

    @Test
    fun testSavingInPreferences() = runBlocking {
        val preference = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        // set False
        OnBoardingCompleteUseCase(preference).invoke(false)

        val actual = OnBoardingCompletedUseCase(preference).invoke()
        assertEquals(false, actual)

        // save true
        OnBoardingCompleteUseCase(preference).invoke(true)
        // Now check if true
        val saved = OnBoardingCompletedUseCase(preference).invoke()
        assertEquals(true, saved)
    }
}