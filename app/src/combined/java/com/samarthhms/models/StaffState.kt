package com.samarthhms.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.samarthhms.constants.Gender
import com.samarthhms.constants.SchemaName

@Entity(tableName = SchemaName.STAFF_STATE_TABLE)
data class StaffState(
    @PrimaryKey
    var key: String,

    @ColumnInfo(name = "staff_id")
    var id: String? = null,

    @ColumnInfo(name = "admin_id")
    var adminId: String? = null,

    @ColumnInfo(name = "first_name")
    override var firstName: String,

    @ColumnInfo(name = "middle_name")
    override var middleName: String? = null,

    @ColumnInfo(name = "last_name")
    override var lastName: String,

    @ColumnInfo(name = "gender")
    var gender: Gender = Gender.MALE
): Name()
