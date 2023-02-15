package com.samarthhms.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.StoredState

@Dao
interface StoredStateDao {
    @Insert
    suspend fun insert(storedState: StoredState)

    @Update
    suspend fun update(storedState: StoredState)

    @Query("DELETE FROM "+SchemaName.STORED_STATE_TABLE+" WHERE `key` = :key")
    suspend fun delete(key: String)

    @Query("SELECT * FROM "+SchemaName.STORED_STATE_TABLE+" WHERE `key` = :key")
    fun get(key: String): StoredState?

    @Query("SELECT id FROM "+SchemaName.STORED_STATE_TABLE+" WHERE `key` = :key")
    fun getId(key: String): String?
}