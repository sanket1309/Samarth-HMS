package com.samarthhms.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.*
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.xml.validation.Schema

class StaffRepositoryImpl @Inject constructor(): StaffRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getStaff(staffId: String): Staff? {
        val reference = db.collection(SchemaName.STAFF_COLLECTION)
        val document = reference.document(staffId)
        try {
            val snapshot = document.get().await()
            if(Objects.isNull(snapshot)){
                Log.i("Staff_Repository_Impl", "No staff found for staff id $staffId")
                return null
            }
            val staffFirebase = snapshot!!.toObject(StaffFirebase::class.java)!!
            val staff = Converters.convertToStaff(staffFirebase)
            Log.i("Staff_Repository_Impl", "Fetched staff for staff id $staffId : [$staff]")
            return staff
        }catch (e: Exception){
            Log.e("Staff_Repository_Impl", "Error while fetching staff : $e")
            throw e
        }
    }

    override suspend fun getAllStaff(adminId: String): List<Staff> {
        try {
            val reference = db.collection(SchemaName.STAFF_COLLECTION)
            val snapshots = reference.whereEqualTo(SchemaName.ADMIN_ID, adminId).get().await()
            if(Objects.isNull(snapshots)){
                Log.i("Staff_Repository_Impl", "No staff found")
                return listOf()
            }
            val staff = snapshots.map { Converters.convertToStaff(it.toObject(StaffFirebase::class.java)) }
            Log.i("Staff_Repository_Impl", "Fetched ${staff.size} staff records")
            return staff
        }catch (e: Exception){
            Log.e("Staff_Repository_Impl", "Error while fetching all staff : $e")
            throw e
        }
    }

    override suspend fun addStaff(staff: Staff):String? {
        val reference = db.collection(SchemaName.STAFF_COLLECTION)
        val document = if(staff.staffId.isBlank()) reference.document() else reference.document(staff.staffId)
        staff.staffId = document.id
        return try {
            document.set(Converters.convertToStaffFirebase(staff))
            Log.i("Staff_Repository_Impl", "Successfully added staff : [$staff]")
            return staff.staffId
        }catch (e: Exception){
            Log.e("Staff_Repository_Impl", "Error while adding staff : $e")
            null
        }
    }

    override suspend fun removeStaff(staffId: String) {
        val reference = db.collection(SchemaName.STAFF_COLLECTION)
        val referenceDelete = db.collection(SchemaName.STAFF_DELETE_COLLECTION)
        val document = reference.document(staffId)
        val documentDelete = referenceDelete.document(staffId)
        try {
            val staff = document.get().await().toObject(StaffFirebase::class.java)!!
            document.delete()
            documentDelete.set(staff)
            Log.i("Staff_Repository_Impl", "Successfully removed staff : [$staff]")
        }catch (e: Exception){
            Log.e("Staff_Repository_Impl", "Error while removing staff : $e")
        }
    }
}