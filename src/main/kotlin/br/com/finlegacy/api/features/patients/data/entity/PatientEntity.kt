package br.com.finlegacy.api.features.patients.data.entity

import br.com.finlegacy.api.features.clinics.data.entity.ClinicEntity
import br.com.finlegacy.api.features.maritalStatus.data.entity.MaritalStatusEntity
import br.com.finlegacy.api.features.occupations.data.entity.OccupationEntity
import br.com.finlegacy.api.features.patients.data.table.PatientTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class PatientEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PatientEntity>(PatientTable)

    var fullName by PatientTable.fullName
    var cpf by PatientTable.cpf
    var rg by PatientTable.rg
    var phoneNumber by PatientTable.phoneNumber
    var email by PatientTable.email
    var birthDate by PatientTable.birthDate
    var addressCEP by PatientTable.addressCEP
    var addressStreet by PatientTable.addressStreet
    var addressNumber by PatientTable.addressNumber
    var addressNeighborhood by PatientTable.addressNeighborhood
    var addressCity by PatientTable.addressCity
    var addressState by PatientTable.addressState

    // Relationship to MaritalStatus
    var maritalStatus by MaritalStatusEntity referencedOn PatientTable.maritalStatus
    var spouseName by PatientTable.spouseName
    var spouseCpf by PatientTable.spouseCpf

    // Relationship to MaritalStatus
    var occupation by OccupationEntity referencedOn PatientTable.occupation
    var income by PatientTable.income

    // Relationship to Clinic
    var clinic by ClinicEntity referencedOn PatientTable.clinic
}
