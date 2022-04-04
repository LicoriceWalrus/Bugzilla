package com.bugzilla.features.bugs.domain.repo

import com.bugzilla.data.getOrThrow
import com.bugzilla.features.bugs.data.api.BugsApi
import com.bugzilla.features.bugs.data.dto.BugDto
import com.bugzilla.features.bugs.domain.entity.Bug
import com.bugzilla.room.support.BugDao
import com.bugzilla.room.support.BugItem
import java.text.SimpleDateFormat
import java.util.*

class BugsRepoImpl(
    private val api: BugsApi,
    private val dao: BugDao
) : BugsRepo {
    override suspend fun searchBugs(query: String): List<Bug> {
        dao.deleteAll()
        val resp = api.searchBugs(query).getOrThrow()
        resp.bugs?.map {
            dao.insertAll(it.mapToDao())
        }
        return resp.bugs?.map {
            it.mapToEntity()
        } ?: emptyList()
    }

    override suspend fun searchBugById(query: String): List<Bug> {
        val resp = api.searchBugById(query).getOrThrow()
        return resp.bugs?.map {
            it.mapToEntity()
        } ?: emptyList()
    }

    override suspend fun getBugsFromBD(): List<Bug> =
        dao.getAll().map {
            it.mapToEntity()
        }

    private fun BugItem.mapToEntity() = Bug(
        id = this.bugId,
        summary = this.summary.orEmpty(),
        creationTime = this.creationTime.orEmpty(),
        creator = this.creator.orEmpty(),
        severity = this.severity.orEmpty(),
        status = this.status.orEmpty()
    )

    private fun BugDto.mapToEntity() = Bug(
        id = this.id,
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