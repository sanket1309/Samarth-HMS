package com.samarthhms.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.StaffState

@Dao
interface StaffStateDao {
    @Insert
    suspend fun insert(staffState: StaffState)

    @Update
    suspend fun update(staffState: StaffState)

    @Query("SELECT * FROM "+SchemaName.STAFF_STATE_TABLE+" WHERE `key` = :key")
    fun get(key: String): StaffState?

    @Query("SELECT admin_id FROM "+SchemaName.STAFF_STATE_TABLE+" WHERE `key` = :key")
    fun getAdminId(key: String): String?

    @Query("SELECT staff_id FROM "+SchemaName.STAFF_STATE_TABLE+" WHERE `key` = :key")
    fun getStaffId(key: String): String?
}