package com.samarthhms.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.Converters
import com.samarthhms.models.Staff
import com.samarthhms.models.StaffFirebase
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

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

    override suspend fun addStaff(staff: Staff) {
        val reference = db.collection(SchemaName.STAFF_COLLECTION)
        val document = reference.document()
        staff.staffId = document.id
        try {
            document.set(Converters.convertToStaffFirebase(staff))
            Log.i("Staff_Repository_Impl", "Successfully added staff : [$staff]")
        }catch (e: Exception){
            Log.e("Staff_Repository_Impl", "Error while adding staff : $e")
        }
    }
}