package com.samarthhms.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.*
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class PatientHistoryTemplateRepositoryImpl @Inject constructor() : PatientHistoryTemplateRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getTemplate(templateId: String): PatientHistoryTemplate?{
        try {
            val reference = db.collection(SchemaName.PATIENT_HISTORY_TEMPLATE_COLLECTION)
            val document = reference.document(templateId)
            val snapshot = document.get().await()
            if(Objects.isNull(snapshot)){
                Log.i("Patient_History_Template_Repository_Impl", "No template found for templateId $templateId")
                return null
            }
            val template = snapshot!!.toObject(PatientHistoryTemplate::class.java)!!
            Log.i("Patient_History_Template_Repository_Impl", "Fetched template for templateId $templateId")
            return template
        }catch (e: Exception){
            Log.e("Patient_History_Template_Repository_Impl", "Error while fetching template : $e")
            throw e
        }
    }

    override suspend fun getAllTemplates(): List<PatientHistoryTemplate> {
        val reference = db.collection(SchemaName.PATIENT_HISTORY_TEMPLATE_COLLECTION)
        try {
            val snapshots = reference.get().await()
            if(!snapshots.isEmpty){
                val templates = snapshots.documents.map { it.toObject(PatientHistoryTemplate::class.java)!! }
                Log.i("Patient_History_Template_Repository_Impl", "Fetched ${templates.size} templates")
                return templates
            }
            Log.i("Patient_History_Template_Repository_Impl", "No templates found")
            return listOf()
        }catch (e: Exception){
            Log.e("Patient_History_Template_Repository_Impl", "Error while fetching all templates : $e")
            throw e
        }
    }

    override suspend fun addTemplate(template: PatientHistoryTemplate){
        try {
            val reference = db.collection(SchemaName.PATIENT_HISTORY_TEMPLATE_COLLECTION)
            val document = reference.document()
            template.templateId = document.id
            document.set(template)
            Log.i("Patient_History_Template_Repository_Impl", "Added template successfully")
        }catch (e: Exception){
            Log.e("Patient_History_Template_Repository_Impl", "Error while adding template : $e")
        }
    }

    override suspend fun deleteTemplate(templateId: String){
        try {
            val reference = db.collection(SchemaName.PATIENT_HISTORY_TEMPLATE_COLLECTION)
            val document = reference.document(templateId)
            document.delete()
            Log.i("Patient_History_Template_Repository_Impl", "Deleted template successfully")
        }catch (e: Exception){
            Log.e("Patient_History_Template_Repository_Impl", "Error while deleting template : $e")
        }
    }

    override suspend fun updateTemplate(template: PatientHistoryTemplate){
        try {
            val reference = db.collection(SchemaName.PATIENT_HISTORY_TEMPLATE_COLLECTION)
            val document = reference.document(template.templateId)
            template.templateId = document.id
            document.set(template)
            Log.i("Patient_History_Template_Repository_Impl", "Updated template successfully")
        }catch (e: Exception){
            Log.e("Patient_History_Template_Repository_Impl", "Error while updating template : $e")
        }
    }
}