package com.samarthhms.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.Admin
import com.samarthhms.models.Converters
import com.samarthhms.models.AdminFirebase
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(): AdminRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getAdmin(adminId: String): Admin? {
        try {
            val reference = db.collection(SchemaName.ADMINS_COLLECTION)
            val document = reference.document(adminId)
            val snapshot = document.get().await()
            if(Objects.isNull(snapshot)){
                Log.i("Admin_Repository_Impl", "No admin found for adminId $adminId")
                return null
            }
            val adminFirebase = snapshot!!.toObject(AdminFirebase::class.java)!!
            val admin = Converters.convertToAdmin(adminFirebase)
            Log.i("Admin_Repository_Impl", "Fetched admin for adminId $adminId = [$admin]")
            return admin
        }catch (e: Exception){
            Log.e("Admin_Repository_Impl", "Error while fetching admin : $e")
            throw e
        }
    }

    override suspend fun getAllAdmins(): List<Admin> {
        try {
            val reference = db.collection(SchemaName.ADMINS_COLLECTION)
            val snapshots = reference.get().await()
            if(Objects.isNull(snapshots)){
                Log.i("Admin_Repository_Impl", "No admin found")
                return listOf()
            }
            val admins = snapshots.map { Converters.convertToAdmin(it.toObject(AdminFirebase::class.java)) }
            Log.i("Admin_Repository_Impl", "Fetched ${admins.size} admins")
            return admins
        }catch (e: Exception){
            Log.e("Admin_Repository_Impl", "Error while fetching all admin : $e")
            throw e
        }
    }

    override suspend fun addAdmin(admin: Admin) {
        try {
            val reference = db.collection(SchemaName.ADMINS_COLLECTION)
            val document = reference.document()
            admin.adminId = document.id
            document.set(Converters.convertToAdminFirebase(admin))
            Log.i("Admin_Repository_Impl", "Added admin successfully")
        }catch (e: Exception){
            Log.e("Admin_Repository_Impl", "Error while adding admin : $e")
        }
    }
}