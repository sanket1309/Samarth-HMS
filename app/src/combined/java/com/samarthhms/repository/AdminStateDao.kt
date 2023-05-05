package com.samarthhms.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.AdminState

@Dao
interface AdminStateDao {
    @Insert
    suspend fun insert(adminState: AdminState)

    @Update
    suspend fun update(adminState: AdminState)

    @Query("DELETE FROM "+SchemaName.ADMIN_STATE_TABLE+" WHERE `key` = :key")
    fun delete(key: String)

    @Query("SELECT * FROM "+SchemaName.ADMIN_STATE_TABLE+" WHERE `key` = :key")
    fun get(key: String): AdminState?

    @Query("SELECT admin_id FROM "+SchemaName.ADMIN_STATE_TABLE+" WHERE `key` = :key")
    fun getAdminId(key: String): String?
}