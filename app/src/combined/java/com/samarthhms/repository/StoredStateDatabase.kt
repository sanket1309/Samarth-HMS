package com.samarthhms.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samarthhms.models.AdminState
import com.samarthhms.models.StaffState
import com.samarthhms.models.StoredState

@Database(entities = [StoredState::class, AdminState::class, StaffState::class], version = 1, exportSchema = false)
abstract class StoredStateDatabase : RoomDatabase() {
    abstract val storedStateDao : StoredStateDao
    abstract val adminStateDao : AdminStateDao
    abstract val staffStateDao : StaffStateDao
}