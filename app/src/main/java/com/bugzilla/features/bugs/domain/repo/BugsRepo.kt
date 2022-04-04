package com.bugzilla.features.bugs.domain.repo

import com.bugzilla.features.bugs.domain.entity.BugDetail
import io.reactivex.rxjava3.core.Single

interface BugsRepo {
    fun searchBugs(query: String): Single<BugDetail>

    fun searchBugById(query: String): Single<BugDetail>

    fun getBugsFromBD(): Single<BugDetail>
}