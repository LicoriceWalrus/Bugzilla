package com.bugzilla.features.bugs.presentations.list

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.bugzilla.features.bugs.domain.entity.Bug
import com.bugzilla.features.bugs.domain.entity.FilterType
import com.bugzilla.features.bugs.domain.interactor.BugsInteractor
import com.bugzilla.room.support.BugDao
import com.bugzilla.searchById
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BugListViewModel(
    private val interactor: BugsInteractor,
    private val sharedPreferences: SharedPreferences,
    dao: BugDao
) : ViewModel() {

    private var state: BugListScreenState =
        BugListScreenState(isSearchById = sharedPreferences.searchById)

    private val screenState: MutableStateFlow<BugListScreenState> = MutableStateFlow(state)

    init {
        state = state.copy(loading = true)
        updateUi()
        dao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ bugs ->
                state = state.copy(loading = false, bugs = bugs.map {
                    it.mapToEntity()
                })
                updateUi()
            }, {
                state = state.copy(loading = false)
                updateUi()
                throw Exception(it.message)
            })
    }

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

    fun changeSearchMethod() {
        sharedPreferences.searchById = sharedPreferences.searchById.not()
        state = state.copy(isSearchById = sharedPreferences.searchById)
        updateUi()
    }

    fun filterTypeChanged(type: FilterType) {
        state = state.copy(filterType = type)
        updateUi()
    }

    fun searchBugs() {
        state = state.copy(loading = true)
        updateUi()
        interactor.searchBugs(
            query = state.query ?: "",
            isSearchById = sharedPreferences.searchById
        )
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

    private fun com.bugzilla.room.support.Bugs.mapToEntity(): Bug =
        Bug(
            id = this.bugId,
            summary = this.summary.orEmpty(),
            creationTime = this.creationTime.orEmpty(),
            creator = this.creator.orEmpty(),
            status = this.status.orEmpty(),
            severity = this.severity.orEmpty(),
        )


}