package com.samarthhms.constants

import com.samarthhms.models.BillItem
import com.samarthhms.models.MedicineTemplate

object Constants {
    object DischargeCardConstants {
        val ADVICE_LIST = listOf(
                MedicineTemplate("","Vaccination (PCV13/influenza)"),
                MedicineTemplate("","Avoid oily & junk food"),
                MedicineTemplate("","Follow up after 5 days.( / /23 with appointment)")
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
}