package com.example.samarthhms.repository

import android.util.Log
import com.example.samarthhms.constants.SchemaName
import com.example.samarthhms.models.Credentials
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LoginRepositoryImpl : LoginRepository{

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
                return data.toObject(Credentials::class.java)
            }
            return null
        }catch (e: Exception){
            Log.e("Firestore_Exception","Error while verifying credentials : $e")
        }
        return null
    }

    override suspend fun getCredentials(id: String): Credentials? {
        val reference = db.collection(SchemaName.LOGIN_CREDENTIALS_COLLECTION)
        val document = reference.document(id)
        try {
            val snapshot = document.get().await()
            if(snapshot.exists()){
                val originalCredentials = snapshot.toObject(Credentials::class.java)
                return originalCredentials!!
            }
        }catch (e: Exception){
            Log.e("Firestore_Exception","Error while fetching credentials : $e")
        }
        return null
    }

    override suspend fun setCredentials(credentials: Credentials) {
        val reference = db.collection(SchemaName.LOGIN_CREDENTIALS_COLLECTION)
        val document = reference.document(credentials.id)
        try {
            val snapshot = document.set(credentials)
        }catch (e: Exception){
            Log.e("Firestore_Exception","Error while setting credentials : $e")
        }
    }
}