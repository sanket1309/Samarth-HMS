package com.samarthhms.staff.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samarthhms.staff.models.AdminState
import com.samarthhms.staff.models.StaffState
import com.samarthhms.staff.models.StoredState

@Database(entities = [StoredState::class, StaffState::class], version = 1, exportSchema = false)
abstract class StoredStateDatabase : RoomDatabase() {
    abstract val storedStateDao : StoredStateDao
    abstract val staffStateDao : StaffStateDao
}