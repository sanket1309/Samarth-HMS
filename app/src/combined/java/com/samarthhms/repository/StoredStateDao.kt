package com.samarthhms.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.samarthhms.models.StoredState

@Dao
interface StoredStateDao {
    @Insert
    suspend fun insert(storedState: StoredState)

    @Update
    suspend fun update(storedState: StoredState)

    @Query("SELECT * FROM stored_state WHERE `key` = :key")
    fun get(key: String): StoredState?

    @Query("SELECT id FROM stored_state WHERE `key` = :key")
    fun getId(key: String): String?
}