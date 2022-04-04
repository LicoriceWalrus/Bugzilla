package com.bugzilla.features.bugs.domain.repo

import com.bugzilla.features.bugs.data.api.BugsApi
import com.bugzilla.features.bugs.data.dto.BugDetailDto
import com.bugzilla.features.bugs.domain.entity.Bug
import com.bugzilla.features.bugs.domain.entity.BugDetail
import com.bugzilla.room.support.BugDao
import io.reactivex.rxjava3.core.Single
import java.text.SimpleDateFormat
import java.util.*

class BugsRepoImpl(
    private val api: BugsApi,
    private val dao: BugDao
) : BugsRepo {
    override fun searchBugs(query: String): Single<BugDetail> =
        api.searchBugs(query).map {
            dao.deleteAll()
            dao.insertAll(*it.mapToDao())
            it.mapToEntity()
        }

    override fun searchBugById(query: String): Single<BugDetail> =
        api.searchBugById(query).map {
            dao.deleteAll()
            dao.insertAll(*it.mapToDao())
            it.mapToEntity()
        }

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


    private fun BugDetailDto.mapToDao(): Array<com.bugzilla.room.support.Bugs> {
        val list = this.bugs?.map { bugDto ->
            com.bugzilla.room.support.Bugs(
                bugId = bugDto.id,
                summary = bugDto.summary,
                creationTime = bugDto.creationTime?.let { date ->
                    SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                    ).format(date).toString()
                } ?: "",
                creator = bugDto.creator,
                status = bugDto.status,
                severity = bugDto.severity,
            )
        }
        return list?.toTypedArray() ?: emptyArray()
    }
}