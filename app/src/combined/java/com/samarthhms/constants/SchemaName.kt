package com.samarthhms.constants

object SchemaName {
    //Login
    const val LOGIN_CREDENTIALS_COLLECTION = "Login_Credentials"
    const val USERNAME = "username"
    const val PASSWORD = "password"
    const val ROLE = "role"

    //Id
    const val ID_COLLECTION = "Id"
    const val CURRENT_PATIENT_ID = "Current_Patient_Id"
    const val CURRENT_PATIENT_ID_FIELD = "patientId"
    const val CURRENT_IPD_NUMBER = "Current_IPD_Number"
    const val CURRENT_IPD_NUMBER_FIELD = "ipdNumber"
    const val CURRENT_BILL_NUMBER = "Current_Bill_Number"
    const val CURRENT_BILL_NUMBER_FIELD = "billNumber"

    //Patient
    const val PATIENTS_COLLECTION = "Patients"
    const val PATIENT_ID = "patientId"

    //Visit
    const val VISITS_COLLECTION = "Visits"

    const val ADMINS_COLLECTION = "Admins"
    const val STAFF_COLLECTION = "Staff"
    const val STAFF_STATUS_COLLECTION = "Staff_Status"

    const val STORED_STATE_DATABASE = "combined_stored_state_database"
    const val STORED_STATE_TABLE = "combined_stored_state_table"
    const val STORED_STATE_KEY = "stored_state_key"
    const val ADMIN_STATE_TABLE = "combined_admin_state_table"
    const val ADMIN_STATE_KEY = "admin_state_key"
    const val STAFF_STATE_TABLE = "combined_staff_state_table"
    const val STAFF_STATE_KEY = "staff_state_key"
}