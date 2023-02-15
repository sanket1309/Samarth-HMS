package com.samarthhms.models

import com.google.firebase.Timestamp
import com.samarthhms.constants.SchemaName
import com.samarthhms.utils.DateTimeUtils
import java.time.LocalDateTime

class Converters {
    companion object{
        fun convertToPatient(patientFirebase: PatientFirebase): Patient{
            return Patient(
                patientFirebase.patientId,
                patientFirebase.firstName,
                patientFirebase.middleName,
                patientFirebase.lastName,
                patientFirebase.gender,
                patientFirebase.contactNumber,
                DateTimeUtils.getLocalDateTime(patientFirebase.dateOfBirth),
                patientFirebase.town,
                patientFirebase.taluka,
                patientFirebase.district
            )
        }

        fun convertToPatientFirebase(patient: Patient): PatientFirebase {
            return PatientFirebase(
                patient.patientId,
                patient.firstName,
                patient.middleName,
                patient.lastName,
                patient.gender,
                patient.contactNumber,
                DateTimeUtils.getTimestamp(patient.dateOfBirth),
                patient.town,
                patient.taluka,
                patient.district
            )
        }

        fun convertToAdmin(adminFirebase: AdminFirebase): Admin{
            return Admin(
                adminFirebase.adminId,
                adminFirebase.firstName,
                adminFirebase.middleName,
                adminFirebase.lastName,
                adminFirebase.gender,
                adminFirebase.contactNumber,
                DateTimeUtils.getLocalDateTime(adminFirebase.dateOfBirth?: Timestamp.now()),
                adminFirebase.town,
                adminFirebase.taluka,
                adminFirebase.district
            )
        }

        fun convertToAdminFirebase(admin: Admin): AdminFirebase {
            return AdminFirebase(
                admin.adminId,
                admin.firstName,
                admin.middleName,
                admin.lastName,
                admin.gender,
                admin.contactNumber,
                DateTimeUtils.getTimestamp(admin.dateOfBirth?: LocalDateTime.now()),
                admin.town,
                admin.taluka,
                admin.district
            )
        }

        fun convertToStaff(staffFirebase: StaffFirebase): Staff{
            return Staff(
                staffFirebase.adminId,
                staffFirebase.staffId,
                staffFirebase.firstName,
                staffFirebase.middleName,
                staffFirebase.lastName,
                staffFirebase.gender,
                staffFirebase.contactNumber,
                DateTimeUtils.getLocalDateTime(staffFirebase.dateOfBirth?:Timestamp.now()),
                staffFirebase.town,
                staffFirebase.taluka,
                staffFirebase.district
            )
        }

        fun convertToStaffFirebase(staff: Staff): StaffFirebase {
            return StaffFirebase(
                staff.adminId,
                staff.staffId,
                staff.firstName,
                staff.middleName,
                staff.lastName,
                staff.gender,
                staff.contactNumber,
                DateTimeUtils.getTimestamp(staff.dateOfBirth?: LocalDateTime.now()),
                staff.town,
                staff.taluka,
                staff.district
            )
        }

        fun convertToAdminState(admin: Admin): AdminState{
            return AdminState(
                SchemaName.ADMIN_STATE_KEY,
                admin.adminId,
                admin.firstName,
                admin.middleName,
                admin.lastName,
                admin.gender,
                admin.dateOfBirth.toString()
            )
        }

        fun convertToStaffState(staff: Staff): StaffState{
            return StaffState(
                SchemaName.STAFF_STATE_KEY,
                staff.staffId,
                staff.adminId,
                staff.firstName,
                staff.middleName,
                staff.lastName,
                staff.gender,
                staff.dateOfBirth.toString()
            )
        }

        fun convertToVisit(visitFirebase: VisitFirebase): Visit{
            return Visit(
                visitFirebase.visitId,
                visitFirebase.patientId,
                visitFirebase.adminId,
                visitFirebase.attendantId,
                visitFirebase.attendantRole,
                DateTimeUtils.getLocalDateTime(visitFirebase.visitTime),
                visitFirebase.isAttended,
                visitFirebase.isAdmitted
            )
        }

        fun convertToVisitFirebase(visit: Visit): VisitFirebase {
            return VisitFirebase(
                visit.visitId,
                visit.patientId,
                visit.adminId,
                visit.attendantId,
                visit.attendantRole,
                DateTimeUtils.getTimestamp(visit.visitTime),
                visit.isAttended,
                visit.isAdmitted
            )
        }

        fun storeStateToStoreStateData(storedState: StoredState): StoredStateData {
            return StoredStateData(
                storedState.role,
                storedState.loggedState,
                storedState.id,
                DateTimeUtils.getLocalDateTime(storedState.entryTime)
            )
        }

        fun storeStateDataToStoreState(storedStateData: StoredStateData): StoredState {
            return StoredState(
                SchemaName.STORED_STATE_KEY,
                storedStateData.role,
                storedStateData.loggedState,
                storedStateData.id,
                storedStateData.logInTime.toString()
            )
        }
    }
}