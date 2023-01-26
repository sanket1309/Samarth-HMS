package com.samarthhms.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samarthhms.models.StoredState

@Database(entities = [StoredState::class], version = 1, exportSchema = false)
abstract class StoredStateDatabase : RoomDatabase() {
    abstract val storedStateDao : StoredStateDao
}