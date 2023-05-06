package com.samarthhms.staff

import android.content.Context
import androidx.room.Room
import com.samarthhms.staff.constants.SchemaName
import com.samarthhms.staff.repository.StaffStateDao
import com.samarthhms.staff.repository.StoredStateDao
import com.samarthhms.staff.repository.StoredStateDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun getContext(@ApplicationContext context : Context) : Context{
        return context
    }

    @Provides
    fun getStoreStateDao(@ApplicationContext context : Context) : StoredStateDao{
        val db = Room.databaseBuilder(context, StoredStateDatabase::class.java,SchemaName.STORED_STATE_DATABASE).build()
        return db.storedStateDao
    }

//    @Provides
//    fun getAdminStateDao(@ApplicationContext context : Context) : AdminStateDao{
//        val db = Room.databaseBuilder(context, StoredStateDatabase::class.java,SchemaName.STORED_STATE_DATABASE).build()
//        return db.adminStateDao
//    }

    @Provides
    fun getStaffStateDao(@ApplicationContext context : Context) : StaffStateDao{
        val db = Room.databaseBuilder(context, StoredStateDatabase::class.java,SchemaName.STORED_STATE_DATABASE).build()
        return db.staffStateDao
    }

}