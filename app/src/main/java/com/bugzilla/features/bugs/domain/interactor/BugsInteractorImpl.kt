package com.bugzilla.features.bugs.domain.interactor

import com.bugzilla.features.bugs.domain.entity.Bug
import com.bugzilla.features.bugs.domain.repo.BugsRepo

class BugsInteractorImpl(
    private val repo: BugsRepo
) : BugsInteractor {
    override suspend fun searchBugs(query: String, isSearchById: Boolean): List<Bug> =
        if (isSearchById) {
            repo.searchBugById(query)
        } else {
            repo.searchBugs(query)
        }

    override suspend fun getBugsFromBD(): List<Bug> =
        repo.getBugsFromBD()
}