package br.com.finlegacy.api.features.patients.data.table

import br.com.finlegacy.api.features.clinics.data.table.ClinicTable
import br.com.finlegacy.api.features.maritalStatus.data.table.MaritalStatusTable
import br.com.finlegacy.api.features.occupations.data.table.OccupationTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object PatientTable : LongIdTable("patients") {
    val fullName = varchar("full_name", 250)
    val cpf = varchar("cpf", 50)
    val rg = varchar("rg", 50)
    val phoneNumber = varchar("phone_number", 50)
    val email = varchar("email", 250)
    val birthDate = varchar("birth_date", 50)

    val addressCEP = varchar("address_cep", 50)
    val addressStreet = varchar("address_street", 250)
    val addressNumber = varchar("address_number", 50)
    val addressNeighborhood = varchar("address_neighborhood", 250)
    val addressCity = varchar("address_city", 250)
    val addressState = varchar("address_state", 100)

    val occupation = reference("occupation_id", OccupationTable, onDelete = ReferenceOption.CASCADE)  // Foreign key to MaritalStatusTable
    val income = double("income")

    //arquivo
    val maritalStatus = reference("marital_status_id", MaritalStatusTable, onDelete = ReferenceOption.CASCADE)  // Foreign key to MaritalStatusTable
    val spouseName = varchar("spouse_name", 250).nullable()  // Allows null values
    val spouseCpf = varchar("spouse_cpf", 50).nullable()    // Allows null values

    val clinic = reference("clinic_id", ClinicTable, onDelete = ReferenceOption.CASCADE)

    init {
        // Uniqueness constraints to ensure these fields are unique within a clinic
        uniqueIndex("unique_cpf_per_clinic", clinic, cpf)
        uniqueIndex("unique_rg_per_clinic", clinic, rg)
        uniqueIndex("unique_phone_per_clinic", clinic, phoneNumber)
        uniqueIndex("unique_email_per_clinic", clinic, email)
    }
}
