package com.bugzilla.features.bugs.presentations.list

import com.bugzilla.features.bugs.domain.entity.Bug

data class BugListScreenState(
    val loading: Boolean = false,
    val bugs: List<Bug> = emptyList(),
    val errorMessage: String? = null
)