package com.bugzilla.features.bugs.presentations.list

import androidx.lifecycle.ViewModel
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

    private fun getBugInfo(id: Int) {
        state = state.copy(loading = true)
        updateUi()
        interactor.getBugDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                state = state.copy(bugs = it.bugs, loading = false)
                updateUi()
            }, {
                state = state.copy(errorMessage = it.message, loading = false)
                updateUi()
            })
    }

    private fun updateUi() {
        screenState.tryEmit(state)
    }

}