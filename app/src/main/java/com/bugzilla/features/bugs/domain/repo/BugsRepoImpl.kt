package com.bugzilla.features.bugs.domain.repo

import com.bugzilla.features.bugs.data.api.BugsApi
import com.bugzilla.features.bugs.data.dto.BugDetailDto
import com.bugzilla.features.bugs.domain.entity.Bug
import com.bugzilla.features.bugs.domain.entity.BugDetail
import io.reactivex.rxjava3.core.Single
import java.text.SimpleDateFormat
import java.util.*

class BugsRepoImpl(
    private val api: BugsApi
) : BugsRepo {
    override fun searchBugs(query: String): Single<BugDetail> =
        api.searchBugs(query).map {
            it.mapToEntity()
        }

    override fun searchBugById(query: String): Single<BugDetail> =
        api.searchBugById(query).map {
            it.mapToEntity()
        }

    private fun BugDetailDto.mapToEntity() = BugDetail(
        bugs = this.bugs?.map {
            Bug(
                id = it.id.toString(),
                summary = it.summary.orEmpty(),
                creationTime = it.creationTime?.let { date ->
                    SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                    ).format(date).toString()
                } ?: "",
                creator = it.creator.orEmpty(),
                severity = it.severity.orEmpty(),
                status = it.status.orEmpty()
            )
        }.orEmpty()
    )
}