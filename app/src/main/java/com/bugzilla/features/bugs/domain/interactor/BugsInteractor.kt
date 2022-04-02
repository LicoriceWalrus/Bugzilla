package com.bugzilla.features.bugs.domain.interactor

import com.bugzilla.features.bugs.domain.entity.BugDetail
import io.reactivex.rxjava3.core.Single

interface BugsInteractor {
    fun getBugDetail(id: Int): Single<BugDetail>
}