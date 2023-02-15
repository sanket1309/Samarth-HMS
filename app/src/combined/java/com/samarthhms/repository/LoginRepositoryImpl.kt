package com.samarthhms.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.Credentials
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(): LoginRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun verifyCredentials(credentials: Credentials): Credentials? {
        val reference = db.collection(SchemaName.LOGIN_CREDENTIALS_COLLECTION)
        val query = reference.whereEqualTo(SchemaName.USERNAME,credentials.username)
                                .whereEqualTo(SchemaName.PASSWORD, credentials.password)
                                .whereEqualTo(SchemaName.ROLE, credentials.role)

        try {
            val snapshot = query.get().await()
            if(snapshot.documents.isNotEmpty()) {
                val data = snapshot.documents.first()
                val originalCredentials = data.toObject(Credentials::class.java)
                Log.i("Login_Repository_Impl", "Fetched matching credentials")
                return originalCredentials
            }
            Log.i("Login_Repository_Impl", "Couldn't find matching credentials")
            return null
        }catch (e: Exception){
            Log.e("Login_Repository_Impl", "Error while verifying credentials : $e")
            throw e
        }
    }

    override suspend fun getCredentials(id: String): Credentials? {
        try {
            val reference = db.collection(SchemaName.LOGIN_CREDENTIALS_COLLECTION)
            val document = reference.document(id)
            val snapshot = document.get().await()
            if(snapshot.exists()){
                val originalCredentials = snapshot.toObject(Credentials::class.java)
                Log.i("Login_Repository_Impl", "Fetched matching credentials")
                return originalCredentials!!
            }
            Log.i("Login_Repository_Impl", "Couldn't find matching credentials")
            return null
        }catch (e: Exception){
            Log.e("Login_Repository_Impl", "Error while fetching credentials : $e")
            throw e
        }
    }

    override suspend fun setCredentials(credentials: Credentials) {
        try {
            val reference = db.collection(SchemaName.LOGIN_CREDENTIALS_COLLECTION)
            val document = reference.document(credentials.id)
            document.set(credentials)
            Log.i("Login_Repository_Impl", "Successfully set credentials")
        }catch (e: Exception){
            Log.e("Login_Repository_Impl", "Error while setting credentials : $e")
        }
    }
}