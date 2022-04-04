package com.bugzilla.features.bugs.domain.repo

import com.bugzilla.features.bugs.domain.entity.Bug

interface BugsRepo {
    suspend fun searchBugs(query: String): List<Bug>

    suspend fun searchBugById(query: String): List<Bug>

    suspend fun getBugsFromBD(): List<Bug>
}