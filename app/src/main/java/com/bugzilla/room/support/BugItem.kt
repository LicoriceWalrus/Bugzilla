package com.bugzilla.room.support

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BugItem(
    @PrimaryKey val bugId: Int,
    @ColumnInfo(name = "summary") val summary: String?,
    @ColumnInfo(name = "creationTime") val creationTime: String?,
    @ColumnInfo(name = "creator") val creator: String?,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "severity") val severity: String?,
)