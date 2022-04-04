package com.bugzilla.features.bugs

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.bugzilla.features.bugs.data.api.BugsApi
import com.bugzilla.features.bugs.domain.interactor.BugsInteractor
import com.bugzilla.features.bugs.domain.interactor.BugsInteractorImpl
import com.bugzilla.features.bugs.domain.repo.BugsRepo
import com.bugzilla.features.bugs.domain.repo.BugsRepoImpl
import com.bugzilla.features.bugs.presentations.list.BugListViewModel
import com.bugzilla.room.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val bugsModule = module {
    single<SharedPreferences> {
        PreferenceManager.getDefaultSharedPreferences(
            androidContext()
        )
    }
    single<BugsApi> {
        get<Retrofit>()
            .create(BugsApi::class.java)
    }
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java, "database"
        ).build()
    }
    single { get<AppDatabase>().bugDao() }
    single<BugsRepo> {
        BugsRepoImpl(get(), get())
    }
    single<BugsInteractor> {
        BugsInteractorImpl(get())
    }
    viewModel { BugListViewModel(get(), get(), get()) }
}