package com.samarthhms.repository

import android.util.Log
import com.samarthhms.constants.Role
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.*
import com.samarthhms.repository.AdminStateDao
import com.samarthhms.repository.StaffStateDao
import com.samarthhms.repository.StoredStateDao
import com.samarthhms.repository.StoredStateRepository
import javax.inject.Inject

class StoredStateRepositoryImpl @Inject constructor() : StoredStateRepository {

    @Inject
    lateinit var storedStateDao: StoredStateDao

    @Inject
    lateinit var adminStateDao: AdminStateDao

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

    override suspend fun getAdminId(): String? {
        try {
            val storedState = storedStateDao.get(SchemaName.STORED_STATE_KEY)
            if(storedState!!.role == Role.ADMIN){
                val adminState = adminStateDao.get(SchemaName.ADMIN_STATE_KEY)!!
                Log.i("Stored_State_Repository_Impl", "Fetched admin Id : ${adminState.adminId}")
                return adminState.adminId
            } else if(storedState.role == Role.STAFF){
                val staffState = staffStateDao.get(SchemaName.STAFF_STATE_KEY)!!
                Log.i("Stored_State_Repository_Impl", "Fetched admin Id : ${staffState.adminId}")
                return staffState.adminId
            }
            return null
        }catch (e: Exception){
            Log.e(
                "Stored_State_Repository_Impl",
                "Error while fetching Admin Id state : ${e.message} => $e"
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

    override suspend fun getAdminState(): AdminState {
        try {
            val adminState = adminStateDao.get(SchemaName.ADMIN_STATE_KEY)
            return adminState!!
        }catch (e: Exception){
            Log.e("Stored_State_Repository_Impl", "Error while fetching Admin state : $e")
            throw e
        }
    }

    override suspend fun setAdminState(admin: Admin) {
        try {
            val adminState = Converters.convertToAdminState(admin)
            val presentAdminState = adminStateDao.get(SchemaName.ADMIN_STATE_KEY)
            if(presentAdminState == null){
                adminStateDao.insert(adminState)
            }
            else{
                adminStateDao.update(adminState)
            }
            Log.i("Stored_State_Repository_Impl", "Successfully set adminState : $adminState")
        }catch (e: Exception){
            Log.e("Stored_State_Repository_Impl", "Error while Updating Admin state : $e")
            throw e
        }
    }

    override suspend fun getSwitchAdminState(): AdminState {
        try {
            val adminState = adminStateDao.get(SchemaName.SWITCH_ADMIN_STATE_KEY)
            return adminState!!
        }catch (e: Exception){
            Log.e("Stored_State_Repository_Impl", "Error while fetching Switch Admin state : $e")
            throw e
        }
    }

    override suspend fun setSwitchAdminState(admin: Admin) {
        try {
            val adminState = Converters.convertToAdminState(admin)
            val presentAdminState = adminStateDao.get(SchemaName.SWITCH_ADMIN_STATE_KEY)
            if(presentAdminState == null){
                adminStateDao.insert(adminState)
            }
            else{
                adminStateDao.update(adminState)
            }
            Log.i("Stored_State_Repository_Impl", "Successfully set Switch adminState : $adminState")
        }catch (e: Exception){
            Log.e("Stored_State_Repository_Impl", "Error while Updating Switch Admin state : $e")
            throw e
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