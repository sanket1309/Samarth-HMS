package com.samarthhms.constants

import com.samarthhms.R
import com.samarthhms.models.BillItem
import com.samarthhms.models.MedicineTemplate
import java.time.LocalDateTime

object Constants {
    object DischargeCardConstants {
        val ADVICE_LIST = listOf(
                MedicineTemplate(templateData = "Vaccination (PCV13/influenza)"),
                MedicineTemplate(templateData = "Avoid oily & junk food"),
                MedicineTemplate(templateData = "Follow up after 5 days.( / /23 with appointment)")
        )
    }

    object BillConstants {
        val DEFAULT_TREATMENT_CHARGES = listOf(
            BillItem("Registration", 350, 0),
            BillItem("Consultation/ Doctors/ Round charges", 800, 0),
            BillItem("Nursing charges", 450, 0),
            BillItem("Room/ Bed charges (Special room)", 1500, 0),
            BillItem("BMW", 100, 0)
        )

        val DEFAULT_MANAGEMENT_CHARGES = listOf(
            BillItem("Procedure IV Canula charges", 200, 0),
            BillItem("Fluid management", 200, 0),
            BillItem("Emergency charges", 500, 0),
            BillItem("Nebulization charges", 20, 0)
        )

        const val OTHER_CHARGES_NAME = "Other Charges"
    }

    const val EMPTY = ""
    const val SPACE = " "
    const val COMMA = ","
    const val HYPHEN = "-"
    const val DOT = "."
    const val COLON = ":"

    const val SHOW = "Show"
    const val HIDE = "Hide"

    const val LOCK = "LOCK"
    const val UNLOCK = "UNLOCK"

    object Messages{
        const val ADMIN_DETAILS_UPDATED = "Updated admin details"
        const val ADMIN_DELETED = "Admin has been removed"
        const val NOTHING_UPDATED = "Nothing has been updated"
        const val SOMETHING_WENT_WRONG = "Something went wrong"
        const val INVALID_TEMPLATE_NAME = "Template name is invalid"
        const val INVALID_TEMPLATE = "Template data is invalid"
        const val PERMISSION_DENIED_GENERATE_BILL = "Permission denied, cannot generate bill"
        const val PERMISSION_DENIED_GENERATE_DISCHARGE_CARD = "Permission denied, cannot generate discharge card"
        const val IPD_NUMBER_ALREADY_EXISTS = "IPD Number already exists"
    }

    object Permissons{
        val PERMISSION_LIST = listOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val REQUEST_CODE_READ_WRITE = 1239871
    }

    object Drawables{
        const val EDITABLE_EDITTEXT = R.drawable.login_edittext_background
        const val UNEDITABLE_EDITTEXT = R.drawable.add_visit_edittext_background
        val LIST_ITEM_BACKGROUNDS = listOf(
            R.drawable.patient_info_background_red,
            R.drawable.patient_info_background_green,
            R.drawable.patient_info_background_blue)
    }

    object DialogMessages{
        const val UPDATE_ADMIN = "Are you sure, you want to update admin?"
        const val DELETE_ADMIN = "Are you sure, you want to delete admin?"
        const val YES = "Yes"
        const val NO = "No"
    }

    object DefaultValues{
        const val ID = "DEFAULT_ID_1231298u"
        const val NAME = "DEFAULT_NAME_1123wq3"
        const val TEMPLATE_DATA = "DEFAULT_TEMPLATE_DATA"
        const val ADDRESS = "DEFAULT_ADDRESS_shd7238y4"
        const val CONTACT_NUMBER = "0000000000"
        const val TOWN = "DINDORI"
        const val TALUKA = "DINDORI"
        const val DISTRICT = "NASHIK"
        const val AGE_TEXT = "0Y 0M"
        const val WEIGHT = 0f
        const val HEIGHT = 0f
        val GENDER = Gender.MALE
        val ROLE = Role.NONE
        val DATE = LocalDateTime.of(1980, 8, 12, 0, 0, 0)
    }
}