package com.samarthhms.repository

import com.samarthhms.models.MedicineTemplate

interface MedicineTemplateRepository {
    suspend fun getTemplate(templateId: String): MedicineTemplate?
    suspend fun getAllTemplates(): List<MedicineTemplate>
    suspend fun addTemplate(template: MedicineTemplate)
    suspend fun updateTemplate(template: MedicineTemplate)
    suspend fun deleteTemplate(templateId: String)
}