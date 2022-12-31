package com.example.samarthhms.repository

import android.util.Log
import com.example.samarthhms.constants.StoredStateConstants
import com.example.samarthhms.models.StoredState
import com.example.samarthhms.models.StoredStateData
import com.example.samarthhms.utils.DateTimeUtils
import javax.inject.Inject

class StoredStateRepositoryImpl @Inject constructor() : StoredStateRepository{

    @Inject
    lateinit var storedStateDao: StoredStateDao

    override suspend fun getStoredState(): StoredStateData {
        return try {
            val storedState = storedStateDao.get(StoredStateConstants.STORED_STATE_KEY)
            var storedStateData = StoredStateData()
            if(storedState != null){
                storedStateData = storeStateToStoreStateData(storedState)
            }
            storedStateData
        }catch (e: Exception){
            Log.e("StoredStateError","Error while fetching Stored state : $e")
            StoredStateData()
        }
    }

    override suspend fun setStoredState(storedStateData: StoredStateData) {
        try {
            val storedState = storeStateDataToStoreState(storedStateData)
            storedStateDao.insert(storedState)
        }catch (e: Exception){
            Log.e("StoredStateError","Error while setting Stored state : $e")
        }
    }

    override suspend fun getId(): String? {
        return try {
            storedStateDao.getId(StoredStateConstants.STORED_STATE_KEY)
        }catch (e: Exception){
            Log.e("StoredStateError","Error while fetching Id from stored state : $e")
            null
        }
    }

    private fun storeStateToStoreStateData(storedState: StoredState): StoredStateData{
        return StoredStateData(
            storedState.role,
            storedState.loggedState,
            storedState.id,
            DateTimeUtils.getLocalDateTime(storedState.entryTime)
        )
    }

    private fun storeStateDataToStoreState(storedStateData: StoredStateData): StoredState{
        return StoredState(
            StoredStateConstants.STORED_STATE_KEY,
            storedStateData.role,
            storedStateData.loggedState,
            storedStateData.id,
            storedStateData.logInTime.toString()
        )
    }

}