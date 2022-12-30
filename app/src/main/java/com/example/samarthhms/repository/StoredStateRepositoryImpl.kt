package com.example.samarthhms.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.samarthhms.constants.LoggedState
import com.example.samarthhms.constants.Role
import com.example.samarthhms.constants.StoredState
import com.example.samarthhms.models.StoredStateData
import com.example.samarthhms.utils.DateTimeUtils

class StoredStateRepositoryImpl(context: Context) : StoredStateRepository{

    private lateinit var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(StoredState.LOG_STATE_FILE,Context.MODE_PRIVATE)
    }

    override fun getStoredState(): StoredStateData {
        try {
            val role = sharedPreferences.getString(StoredState.ROLE, Role.NONE.value)
            val logState =
                sharedPreferences.getString(StoredState.LOG_STATE, LoggedState.LOGGED_OUT.value)
            val id = sharedPreferences.getString(StoredState.ID, null)
            val logInTime = sharedPreferences.getString(StoredState.LAST_LOGIN, null)
            return StoredStateData(
                Role.getByValue(role),
                LoggedState.getByValue(logState),
                id,
                DateTimeUtils.getLocalDateTime(logInTime)
            )
        }catch (e: Exception){
            Log.e("StoredStateError","Error while fetching Stored state : $e")
            return StoredStateData()
        }
    }

    override fun setStoredState(storedStateData: StoredStateData) {
        try {
            with(sharedPreferences.edit()){
                putString(StoredState.ROLE, storedStateData.role.value)
                putString(StoredState.LOG_STATE, storedStateData.loggedState.value)
                putString(StoredState.ID, storedStateData.id)
                putString(StoredState.LAST_LOGIN, storedStateData.logInTime.toString())
                apply()
            }
        }catch (e: Exception){
            Log.e("StoredStateError","Error while setting Stored state : $e")
        }
    }

    override fun getId(): String? {
        try {
            return sharedPreferences.getString(StoredState.ID, null)
        }catch (e: Exception){
            Log.e("StoredStateError","Error while fetching Id from stored state : $e")
            return null
        }
    }
}