package com.bugzilla.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bugzilla.room.support.BugItem
import com.bugzilla.room.support.BugDao

@Database(entities = [BugItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bugDao(): BugDao
}