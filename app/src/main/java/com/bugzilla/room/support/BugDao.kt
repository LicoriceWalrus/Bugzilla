package com.bugzilla.room.support

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BugDao {
    @Query("SELECT * FROM bugItem")
    suspend fun getAll(): List<BugItem>

    @Query("SELECT * FROM bugItem WHERE bugId IN (:bugIds)")
    suspend fun loadAllByIds(bugIds: IntArray): List<BugItem>

    @Insert
    suspend fun insertAll(vararg bugs: BugItem)

    @Delete
    suspend fun delete(bug: BugItem)

    suspend fun deleteAll() {
        getAll().map {
            delete(it)
        }
    }
}