package com.bugzilla.features.bugs.data.dto

import com.google.gson.annotations.SerializedName

data class BugDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("summary")
    val summary: String? = null,
    @SerializedName("alias")
    val alias: String? = null
)