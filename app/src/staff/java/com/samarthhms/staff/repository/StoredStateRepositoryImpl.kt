package com.samarthhms.staff.repository

import android.util.Log
import com.samarthhms.staff.constants.Role
import com.samarthhms.staff.constants.SchemaName
import com.samarthhms.staff.models.*
import java.util.Objects
import javax.inject.Inject

class StoredStateRepositoryImpl @Inject constructor() : StoredStateRepository {

    @Inject
    lateinit var storedStateDao: StoredStateDao

    @Inject
    lateinit var staffStateDao: StaffStateDao

    override suspend fun getStoredState(): StoredStateData {
        return try {
            val storedState = storedStateDao.get(SchemaName.STORED_STATE_KEY)
            val storedStateData: StoredStateData
            if(storedState != null){
                storedStateData = Converters.storeStateToStoreStateData(storedState)
                Log.i("Stored_State_Repository_Impl", "Fetched stored state : $storedState")
            }else{
                throw Exception("No data found")
            }
            storedStateData
        }catch (e: Exception){
            Log.e(
                "Stored_State_Repository_Impl",
                "Error while fetching Stored state : ${e.message} => $e"
            )
            throw e
        }
    }

    override suspend fun setStoredState(storedStateData: StoredStateData) {
        try {
            val storedState = Converters.storeStateDataToStoreState(storedStateData)
            val presentStoredState = storedStateDao.get(SchemaName.STORED_STATE_KEY)
            if(presentStoredState == null)
                storedStateDao.insert(storedState)
            else
                storedStateDao.update(storedState)
            Log.i("Stored_State_Repository_Impl", "Successfully set storedState : $storedState")
        }catch (e: Exception){
            Log.e("Stored_State_Repository_Impl", "Error while setting Stored state : $e")
        }
    }

    override suspend fun removeStoredState() {
        try {
            storedStateDao.delete(SchemaName.STORED_STATE_KEY)
            Log.i("Stored_State_Repository_Impl", "Successfully deleted storedState")
        }catch (e: Exception){
            Log.e("Stored_State_Repository_Impl", "Error while deleting Stored state : $e")
        }
    }

    override suspend fun getStaffState(): StaffState {
        try {
            val staffState = staffStateDao.get(SchemaName.STAFF_STATE_KEY)!!
            Log.i("Stored_State_Repository_Impl", "Fetched staffState : $staffState")
            return staffState
        }catch (e: Exception){
            Log.e("Stored_State_Repository_Impl", "Error while fetching Staff state : $e")
            throw e
        }
    }

    override suspend fun setStaffState(staff: Staff) {
        try {
            val staffState = Converters.convertToStaffState(staff)
            val presentStaffState = staffStateDao.get(SchemaName.STAFF_STATE_KEY)
            if(presentStaffState == null){
                staffStateDao.insert(staffState)
            }else{
                staffStateDao.update(staffState)
            }
            Log.i("Stored_State_Repository_Impl", "Successfully set staffState : $staffState")
        }catch (e: Exception){
            Log.e("Stored_State_Repository_Impl", "Error while adding Staff state : $e")
            throw e
        }
    }

    override suspend fun getId(): String? {
        return try {
            val id = storedStateDao.getId(SchemaName.STORED_STATE_KEY)!!
            Log.i("Stored_State_Repository_Impl", "Fetched id from storedState : $id")
            id
        }catch (e: Exception){
            Log.e("Stored_State_Repository_Impl", "Error while fetching Id from stored state : $e")
            throw e
        }
    }

}