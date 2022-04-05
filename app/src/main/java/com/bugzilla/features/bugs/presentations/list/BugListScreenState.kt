package com.bugzilla.features.bugs.presentations.list

import com.bugzilla.features.bugs.domain.entity.Bug
import com.bugzilla.features.bugs.domain.entity.FilterType

data class BugListScreenState(
    val loading: Boolean = false,
    val query: String? = null,
    val bugs: List<Bug> = emptyList(),
    val filterType: FilterType = FilterType.ID,
    val isSearchById: Boolean,
    val isRefreshing: Boolean = false,
    val emptyQueryDialog: Boolean = false
)