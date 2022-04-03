package com.bugzilla.features.bugs.presentations.list

import com.bugzilla.features.bugs.domain.entity.Bug

data class BugListScreenState(
    val loading: Boolean = false,
    val query: String? = null,
    val bugs: List<Bug> = emptyList(),
    val message: String? = ""
)