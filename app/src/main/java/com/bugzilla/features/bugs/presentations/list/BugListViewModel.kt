package com.bugzilla.features.bugs.presentations.list

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugzilla.features.bugs.domain.entity.Bug
import com.bugzilla.features.bugs.domain.entity.FilterType
import com.bugzilla.features.bugs.domain.interactor.BugsInteractor
import com.bugzilla.searchById
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BugListViewModel(
    private val interactor: BugsInteractor,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private var state: BugListScreenState =
        BugListScreenState(isSearchById = sharedPreferences.searchById)

    private val screenState: MutableStateFlow<BugListScreenState> = MutableStateFlow(state)

    init {
        state = state.copy(loading = true)
        updateUi()
        viewModelScope.launch {
            runCatching {
                interactor.getBugsFromBD()
            }.onSuccess {
                state = state.copy(bugs = it, loading = false)
                updateUi()
            }.onFailure {
                state = state.copy(message = it.message, loading = false, bugs = emptyList())
                updateUi()
            }
        }
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
        viewModelScope.launch {
            runCatching {
                interactor.searchBugs(
                    query = state.query ?: "",
                    isSearchById = sharedPreferences.searchById
                )
            }.onSuccess {
                state = state.copy(bugs = it, loading = false, message = null)
                updateUi()
            }.onFailure {
                state = state.copy(message = it.message, loading = false, bugs = emptyList())
                updateUi()
            }
        }
    }

    private fun updateUi() {
        screenState.tryEmit(state)
    }

}