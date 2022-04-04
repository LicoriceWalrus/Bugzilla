package com.bugzilla.features.bugs.data.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class BugDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("summary")
    val summary: String? = null,
    @SerializedName("creation_time")
    val creationTime: Date? = null,
    @SerializedName("creator")
    val creator: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("severity")
    val severity: String? = null
)