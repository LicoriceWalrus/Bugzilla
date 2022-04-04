package com.bugzilla.features.bugs.domain.interactor

import com.bugzilla.features.bugs.domain.entity.Bug

interface BugsInteractor {

    suspend fun searchBugs(query: String, isSearchById: Boolean): List<Bug>

    suspend fun getBugsFromBD(): List<Bug>
}