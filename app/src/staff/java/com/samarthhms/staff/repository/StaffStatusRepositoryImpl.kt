package com.samarthhms.staff.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.staff.constants.SchemaName
import com.samarthhms.staff.models.Converters
import com.samarthhms.staff.models.Staff
import com.samarthhms.staff.models.StaffFirebase
import com.samarthhms.staff.models.StaffStatus
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
        val document = reference.document(staffStatus.staffId)
        try {
            document.set(staffStatus)
            Log.i("Staff_Status_Repository_Impl", "Successfully added staff : [$staffStatus]")
        }catch (e: Exception){
            Log.e("Staff_Status_Repository_Impl", "Error while adding staff : $e")
        }
    }

    override suspend fun removeStaffStatus(staffId: String) {
        val reference = db.collection(SchemaName.STAFF_STATUS_COLLECTION)
        val referenceDelete = db.collection(SchemaName.STAFF_STATUS_DELETE_COLLECTION)
        val document = reference.document(staffId)
        val documentDelete = referenceDelete.document(staffId)
        try {
            val staffStatus = document.get().await()
            document.delete()
            documentDelete.set(staffStatus)
            Log.i("Staff_Status_Repository_Impl", "Successfully added staff : [$staffStatus]")
        }catch (e: Exception){
            Log.e("Staff_Status_Repository_Impl", "Error while adding staff : $e")
        }
    }
}