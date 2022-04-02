package com.bugzilla.features.bugs.domain.repo

import com.bugzilla.features.bugs.data.dto.BugDetailDto
import com.bugzilla.features.bugs.domain.entity.BugDetail
import io.reactivex.rxjava3.core.Single

interface BugsRepo {
    fun getBugDetail(id: Int): Single<BugDetail>
}