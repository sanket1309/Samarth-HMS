package com.samarthhms.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.samarthhms.constants.Gender
import com.samarthhms.constants.SchemaName

@Entity(tableName = SchemaName.ADMIN_STATE_TABLE)
data class AdminState(
    @PrimaryKey
    var key: String,

    @ColumnInfo(name = "admin_id")
    var admin_id: String? = null,

    @ColumnInfo(name = "first_name")
    var firstName: String? = null,

    @ColumnInfo(name = "middle_name")
    var middleName: String? = null,

    @ColumnInfo(name = "last_name")
    var lastName: String? = null,

    @ColumnInfo(name = "gender")
    var gender: Gender = Gender.MALE,

    @ColumnInfo(name = "date_of_birth")
    var dateOfBirth: String? = null
)
