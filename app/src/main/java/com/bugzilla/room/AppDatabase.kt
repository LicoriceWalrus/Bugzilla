package com.bugzilla.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bugzilla.room.support.Bugs
import com.bugzilla.room.support.BugDao

@Database(entities = [Bugs::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bugDao(): BugDao
}