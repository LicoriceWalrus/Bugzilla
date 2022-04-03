package com.bugzilla.features.bugs.presentations.list

import androidx.lifecycle.ViewModel
import com.bugzilla.features.bugs.domain.entity.Bug
import com.bugzilla.features.bugs.domain.interactor.BugsInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BugListViewModel(
    private val interactor: BugsInteractor
) : ViewModel() {

    private var state: BugListScreenState = BugListScreenState()

    private val screenState: MutableStateFlow<BugListScreenState> = MutableStateFlow(state)

    fun screenState(): StateFlow<BugListScreenState> = screenState

    fun changeInfoVisibility(bug: Bug) {
        state = state.copy(bugs = state.bugs.map {
            if (it.id == bug.id) {
                it.copy(isMoreInformationMode = it.isMoreInformationMode.not())
            } else {
                it
            }
        })
        updateUi()
    }

    fun onQueryChanged(query: String) {
        state = state.copy(query = query)
        updateUi()
    }

    fun searchBugs() {
        state = state.copy(loading = true)
        updateUi()
        interactor.searchBugs(state.query ?: "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                state = state.copy(bugs = it.bugs, loading = false, message = null)
                updateUi()
            }, {
                state = state.copy(message = it.message, loading = false, bugs = emptyList())
                updateUi()
            })
    }

    private fun updateUi() {
        screenState.tryEmit(state)
    }

}