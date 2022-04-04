package com.bugzilla.room.support

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface BugDao {
    @Query("SELECT * FROM bugs")
    fun getAll(): Single<List<Bugs>>

    @Query("SELECT * FROM bugs WHERE bugId IN (:bugIds)")
    fun loadAllByIds(bugIds: IntArray): List<Bugs>

    @Insert
    fun insertAll(vararg bugs: Bugs)

    @Delete
    fun delete(bug: List<Bugs>)

    fun deleteAll() {
        getAll()
            .subscribe({
            delete(it)
        }, {
            throw Exception(it.message)
        })

    }
}