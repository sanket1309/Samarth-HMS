package com.samarthhms.repository

import com.samarthhms.models.PatientHistoryTemplate

interface PatientHistoryTemplateRepository {
    suspend fun getTemplate(templateId: String): PatientHistoryTemplate?
    suspend fun getAllTemplates(): List<PatientHistoryTemplate>
    suspend fun addTemplate(template: PatientHistoryTemplate)
    suspend fun updateTemplate(template: PatientHistoryTemplate)
    suspend fun deleteTemplate(templateId: String)
}