package com.bugzilla.features.bugs.data.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class BugDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("summary")
    val summary: String? = null,
    @SerializedName("alias")
    val alias: String? = null,
    @SerializedName("creator")
    val creator: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("severity")
    val severity: String? = null
)