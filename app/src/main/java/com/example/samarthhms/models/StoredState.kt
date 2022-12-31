package com.example.samarthhms.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.samarthhms.constants.LoggedState
import com.example.samarthhms.constants.Role
import com.example.samarthhms.constants.SchemaName

@Entity(tableName = SchemaName.STORED_STATE_TABLE)
data class StoredState(
    @PrimaryKey
    var key: String,

    @ColumnInfo(name = "role")
    var role: Role = Role.NONE,

    @ColumnInfo(name = "logged_state")
    var loggedState: LoggedState = LoggedState.LOGGED_OUT,

    @ColumnInfo(name = "id")
    var id: String? = null,

    @ColumnInfo(name = "entry_time")
    var entryTime: String? = null
)
