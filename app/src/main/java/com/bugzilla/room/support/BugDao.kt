package com.bugzilla.room.support

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BugDao {
    @Query("SELECT * FROM bugItem")
    suspend fun getAll(): List<BugItem>

    @Insert
    suspend fun insertAll(vararg bugs: BugItem)

    @Delete
    suspend fun delete(bug: BugItem)

    @Query("DELETE FROM bugItem")
    suspend fun delete()
}