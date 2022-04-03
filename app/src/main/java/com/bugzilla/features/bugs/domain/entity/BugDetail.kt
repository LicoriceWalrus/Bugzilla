package com.bugzilla.features.bugs.domain.entity

import java.util.*

data class BugDetail(
    val bugs: List<Bug>
)

data class Bug(
    val id: String,
    val summary: String,
    val alias: String,
    val creator: String,
    val status: String,
    val severity: String,
    var isMoreInformationMode: Boolean = false
)