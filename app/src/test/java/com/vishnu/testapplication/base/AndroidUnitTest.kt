package com.vishnu.testapplication.base

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.vishnu.testapplication.data.source.MobileBankingApiClient
import com.vishnu.testapplication.data.source.MobileBankingApiClient.createMock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import org.robolectric.annotation.Config

@Ignore("BaseTest")
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(maxSdk = Build.VERSION_CODES.P, minSdk = Build.VERSION_CODES.P)
open class AndroidUnitTest : KoinTest, KoinComponent {

    val context = ApplicationProvider.getApplicationContext<Application>()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun _setUp() {
        Dispatchers.setMain(testDispatcher)
        loadKoinModules(listOf(testDispatchersModule, mockApiClient))
    }

    @After
    fun _cleanUp() {
        stopKoin()
        Dispatchers.resetMain()
        //testDispatcher.cleanupTestCoroutines()
    }

    private val testDispatchersModule = module {
        single<CoroutineDispatcher>(named("IO")) { testDispatcher }
        single<CoroutineDispatcher>(named("Main")) { testDispatcher }
        single<CoroutineDispatcher>(named("Default")) { testDispatcher }
    }

    private val mockApiClient = module {
        single { createMock() }
    }
}