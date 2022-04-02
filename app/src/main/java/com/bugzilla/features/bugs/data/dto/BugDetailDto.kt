package com.bugzilla.features.bugs.data.dto

import com.google.gson.annotations.SerializedName

data class BugDetailDto(
    @SerializedName("bugs")
    val bugs: List<BugDto>? = null
)