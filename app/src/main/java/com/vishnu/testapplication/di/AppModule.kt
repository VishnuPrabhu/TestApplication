package com.vishnu.testapplication.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.vishnu.testapplication.data.source.DefaultDataSource
import com.vishnu.testapplication.data.source.MobileBankingApiClient.getApiClient
import com.vishnu.testapplication.data.source.MobileBankingRepository
import com.vishnu.testapplication.data.source.local.LocalMobileBankingDataSource
import com.vishnu.testapplication.data.source.local.SessionDatabase
import com.vishnu.testapplication.data.source.local.SessionHelper
import com.vishnu.testapplication.data.source.remote.RemoteMobileBankingDataSource
import com.vishnu.testapplication.domain.*
import com.vishnu.testapplication.ui.home.HomeViewModel
import com.vishnu.testapplication.ui.home.TransferViewModel
import com.vishnu.testapplication.ui.login.LaunchViewModel
import com.vishnu.testapplication.ui.login.LoginViewModel
import com.vishnu.testapplication.ui.welcome.PlaceHolderViewModel
import com.vishnu.testapplication.ui.welcome.WelcomeViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.getKoin


val repositoryModule = module {
    single { LocalMobileBankingDataSource() }
    factory { DeleteCacheUseCase(get()) }

    single { getApiClient() }
    single { RemoteMobileBankingDataSource(get()) }

    single<MobileBankingRepository> { DefaultDataSource(get(), get(), Dispatchers.io) }
    single<SharedPreferences> { androidApplication().getSharedPreferences("AppSharedPreferences", Context.MODE_PRIVATE) }
}

val launchModule = module {
    factory { OnBoardingCompleteUseCase(get()) }
    factory { OnBoardingCompletedUseCase(get()) }
    viewModel { LaunchViewModel(get()) }
    viewModel { WelcomeViewModel(get()) }
    viewModel { PlaceHolderViewModel() }
}

val loginModule = module {
    factory { LoginUseCase(get()) }
    viewModel { LoginViewModel(get(), get(named("io"))) }
}

val homeModule = module {
    factory { GetAccountDetailsUseCase(get()) }
    factory { GetTransactionDetailsUseCase(get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
}

val transferModule = module {
    factory { GetPayeesUseCase(get()) }
    factory { TransferAmountUseCase(get()) }
    factory { InvalidateSessionUseCase(get()) }
    viewModel { TransferViewModel(get(), get(), get()) }
}

val coroutinesModule = module {
    factory(named("io")) { Dispatchers.IO }
    factory(named("main")) { Dispatchers.Main }
    factory(named("default")) {  Dispatchers.Default }
}

val databaseModule = module {
    single<SessionDatabase> {
        val db = Room.databaseBuilder(get(), SessionDatabase::class.java, "session_db").build()
        db
    }
    single { SessionHelper(get<SessionDatabase>().sessionDao())}
}

val Dispatchers.io: CoroutineDispatcher
    get() = getKoin().get(named("io"))

val Dispatchers.main: MainCoroutineDispatcher
    get() = getKoin().get(named("main"))

val Dispatchers.default: CoroutineDispatcher
    get() = getKoin().get(named("default"))

val sessionHelper: SessionHelper
    get() = getKoin().get<SessionHelper>()