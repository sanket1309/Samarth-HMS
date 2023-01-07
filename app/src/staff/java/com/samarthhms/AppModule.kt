package com.samarthhms

import android.content.Context
import androidx.room.Room
import com.samarthhms.constants.SchemaName
import com.samarthhms.repository.StoredStateDao
import com.samarthhms.repository.StoredStateDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun getStoreStateDao(@ApplicationContext context : Context) : StoredStateDao{
        val db = Room.databaseBuilder(context, StoredStateDatabase::class.java,SchemaName.STORED_STATE_TABLE).build()
        return db.storedStateDao
    }
}