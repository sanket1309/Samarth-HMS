package com.samarthhms.utils

import android.util.Log
import com.samarthhms.databinding.MedicineTemplateLayoutBinding

class MedicineTemplateDataExtractorUtils {
    companion object{
        fun extractData(medicineTemplateLayoutBinding: MedicineTemplateLayoutBinding): String {
            try {
                return UiDataExtractorUtils.getText(medicineTemplateLayoutBinding.template)
            } catch (e: Exception) {
                Log.e("MedicineTemplateDataExtractorUtils", "Failed to extract medicine template", e)
                throw e
            }
        }
    }
}