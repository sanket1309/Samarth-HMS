package com.samarthhms.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.Converters
import com.samarthhms.models.Staff
import com.samarthhms.models.StaffFirebase
import com.samarthhms.models.StaffStatus
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class StaffStatusRepositoryImpl @Inject constructor(): StaffStatusRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getStaffStatus(staffId: String): StaffStatus? {
        val reference = db.collection(SchemaName.STAFF_STATUS_COLLECTION)
        val document = reference.document(staffId)
        try {
            val snapshot = document.get().await()
            if(Objects.isNull(snapshot)){
                Log.i("Staff_Status_Repository_Impl", "No staff found for staff id $staffId")
                return null
            }
            val staffStatus = snapshot!!.toObject(StaffStatus::class.java)!!
            Log.i("Staff_Status_Repository_Impl", "Fetched staff for staff id $staffId : [${staffStatus.staffId}]")
            return staffStatus
        }catch (e: Exception){
            Log.e("Staff_Status_Repository_Impl", "Error while fetching staff : $e")
            throw e
        }
    }

    override suspend fun addStaffStatus(staffStatus: StaffStatus) {
        val reference = db.collection(SchemaName.STAFF_STATUS_COLLECTION)
        val document = reference.document()
        staffStatus.staffId = document.id
        try {
            document.set(staffStatus)
            Log.i("Staff_Status_Repository_Impl", "Successfully added staff : [$staffStatus]")
        }catch (e: Exception){
            Log.e("Staff_Status_Repository_Impl", "Error while adding staff : $e")
        }
    }
}