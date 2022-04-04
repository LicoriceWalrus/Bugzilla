package com.bugzilla.room.support

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface BugDao {
    @Query("SELECT * FROM bugItem")
    fun getAll(): Single<List<BugItem>>

    @Query("SELECT * FROM bugItem WHERE bugId IN (:bugIds)")
    fun loadAllByIds(bugIds: IntArray): List<BugItem>

    @Insert
    fun insertAll(vararg bugs: BugItem)

    @Delete
    fun delete(bug: BugItem)

    fun deleteAll() {
        getAll().subscribe({
            it.map { bug ->
                delete(bug)
            }
        }, {
            throw Exception(it.message)
        })
    }
}