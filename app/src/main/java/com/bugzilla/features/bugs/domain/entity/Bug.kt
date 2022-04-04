package com.bugzilla.features.bugs.domain.entity

data class Bug(
    val id: Int,
    val summary: String,
    val creationTime: String,
    val creator: String,
    val status: String,
    val severity: String,
    var isMoreInformationMode: Boolean = false
)