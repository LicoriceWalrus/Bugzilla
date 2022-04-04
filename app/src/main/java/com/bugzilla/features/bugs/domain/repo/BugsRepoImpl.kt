package com.bugzilla.features.bugs.domain.repo

import com.bugzilla.features.bugs.data.api.BugsApi
import com.bugzilla.features.bugs.data.dto.BugDetailDto
import com.bugzilla.features.bugs.data.dto.BugDto
import com.bugzilla.features.bugs.domain.entity.Bug
import com.bugzilla.features.bugs.domain.entity.BugDetail
import com.bugzilla.room.support.BugDao
import com.bugzilla.room.support.BugItem
import io.reactivex.rxjava3.core.Single
import java.text.SimpleDateFormat
import java.util.*

class BugsRepoImpl(
    private val api: BugsApi,
    private val dao: BugDao
) : BugsRepo {
    override fun searchBugs(query: String): Single<BugDetail> =
        api.searchBugs(query)
            .doOnSubscribe {
                dao.deleteAll()
            }.doAfterSuccess {
                it.bugs?.map { bug ->
                    dao.insertAll(bug.mapToDao())
                }
            }.map {
                it.mapToEntity()
            }

    override fun searchBugById(query: String): Single<BugDetail> =
        api.searchBugById(query).map {
            it.mapToEntity()
        }

    override fun getBugsFromBD(): Single<BugDetail> =
        dao.getAll().map {
            BugDetail(it.map { bug ->
                bug.mapToEntity()
            })
        }

    private fun BugItem.mapToEntity() = Bug(
        id = this.bugId,
        summary = this.summary.orEmpty(),
        creationTime = this.creationTime.orEmpty(),
        creator = this.creator.orEmpty(),
        severity = this.severity.orEmpty(),
        status = this.status.orEmpty()
    )

    private fun BugDetailDto.mapToEntity() = BugDetail(
        bugs = this.bugs?.map {
            Bug(
                id = it.id,
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

    private fun BugDto.mapToDao() = BugItem(
        bugId = this.id,
        summary = this.summary.orEmpty(),
        creationTime = this.creationTime?.let { date ->
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).format(date).toString()
        } ?: "",
        creator = this.creator.orEmpty(),
        severity = this.severity.orEmpty(),
        status = this.status.orEmpty()
    )
}