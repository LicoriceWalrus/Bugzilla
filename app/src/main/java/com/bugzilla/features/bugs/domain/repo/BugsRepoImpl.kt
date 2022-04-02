package com.bugzilla.features.bugs.domain.repo

import com.bugzilla.features.bugs.data.api.BugsApi
import com.bugzilla.features.bugs.data.dto.BugDetailDto
import com.bugzilla.features.bugs.domain.entity.Bug
import com.bugzilla.features.bugs.domain.entity.BugDetail
import io.reactivex.rxjava3.core.Single

class BugsRepoImpl(
    private val api: BugsApi
) : BugsRepo {
    override fun getBugDetail(id: Int): Single<BugDetail> =
        api.getBugDetail(id).map {
            it.mapToEntity()
        }

    private fun BugDetailDto.mapToEntity() = BugDetail(
        bugs = this.bugs?.map {
            Bug(
                id = it.id.toString(),
                summary = it.summary.orEmpty()
            )
        }.orEmpty()
    )
}