package com.bugzilla.features.bugs.domain.entity

data class BugDetail(
    val bugs: List<Bug>
)

data class Bug(
    val id: String,
    val summary: String
)