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
        viewModelScope.launch {
            state = state.copy(loading = true)
            updateUi()
            runCatching {
                interactor.getBugsFromBD()
            }.onSuccess {
                state = state.copy(
                    bugs = it,
                    loading = false
                )
                updateUi()
            }.onFailure {
                state = state.copy(
                    bugs = emptyList(),
                    loading = false
                )
                updateUi()
            }
        }
    }

    fun screenState(): StateFlow<BugListScreenState> = screenState

    fun changeInfoVisibility(bug: Bug) {
        viewModelScope.launch {
            state = state.copy(bugs = state.bugs.map {
                if (it.id == bug.id) {
                    it.copy(isMoreInformationMode = it.isMoreInformationMode.not())
                } else {
                    it
                }
            })
            updateUi()
        }
    }

    fun onQueryChanged(query: String) {
        viewModelScope.launch {
            state = state.copy(query = query)
            updateUi()
        }
    }

    fun openEmptyQueryDialog(value: Boolean) {
        viewModelScope.launch {
            state = state.copy(emptyQueryDialog = value, query = "")
            updateUi()
        }
    }

    fun changeSearchMethod() {
        viewModelScope.launch {
            sharedPreferences.searchById = sharedPreferences.searchById.not()
            state = state.copy(isSearchById = sharedPreferences.searchById)
            updateUi()
        }
    }

    fun refresh() {
        state.query?.let {
            if (it.isNotBlank()) {
                searchBugs(true)
            }
        }
    }

    fun filterTypeChanged(type: FilterType) {
        viewModelScope.launch {
            state = state.copy(filterType = type)
            updateUi()
        }
    }

    fun searchBugs(isRefresh: Boolean = false) {
        if (state.query.isNullOrBlank()) {
            openEmptyQueryDialog(true)
        } else {
            viewModelScope.launch {
                state = state.copy(loading = !isRefresh, isRefreshing = isRefresh)
                updateUi()
                runCatching {
                    interactor.searchBugs(
                        query = state.query ?: "",
                        isSearchById = sharedPreferences.searchById
                    )
                }.onSuccess {
                    state = state.copy(bugs = it, loading = false, isRefreshing = false)
                    updateUi()
                }.onFailure {
                    state = state.copy(loading = false, bugs = emptyList(), isRefreshing = false)
                    updateUi()
                }
            }
        }
    }

    private suspend fun updateUi() {
        screenState.emit(state)
    }

}