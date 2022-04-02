package com.bugzilla.features.bugs

import com.bugzilla.features.bugs.data.api.BugsApi
import com.bugzilla.features.bugs.domain.interactor.BugsInteractor
import com.bugzilla.features.bugs.domain.interactor.BugsInteractorImpl
import com.bugzilla.features.bugs.domain.repo.BugsRepo
import com.bugzilla.features.bugs.domain.repo.BugsRepoImpl
import com.bugzilla.features.bugs.presentations.list.BugListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val bugsModule = module {
    single<BugsApi> {
        get<Retrofit>()
            .create(BugsApi::class.java)
    }
    single<BugsRepo> {
        BugsRepoImpl(get())
    }
    single<BugsInteractor> {
        BugsInteractorImpl(get())
    }
    viewModel { BugListViewModel(get()) }
}