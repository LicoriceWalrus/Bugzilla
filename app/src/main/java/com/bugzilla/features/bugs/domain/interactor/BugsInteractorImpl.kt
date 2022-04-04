package com.bugzilla.features.bugs.domain.interactor

import com.bugzilla.features.bugs.domain.entity.BugDetail
import com.bugzilla.features.bugs.domain.repo.BugsRepo
import io.reactivex.rxjava3.core.Single

class BugsInteractorImpl(
    private val repo: BugsRepo
) : BugsInteractor {
    override fun searchBugs(query: String, isSearchById: Boolean): Single<BugDetail> =
        if (isSearchById) {
            repo.searchBugById(query)
        } else {
            repo.searchBugs(query)
        }

    override fun getBugsFromBD(): Single<BugDetail> =
        repo.getBugsFromBD()
}