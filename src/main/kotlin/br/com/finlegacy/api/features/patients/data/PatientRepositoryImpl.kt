package br.com.finlegacy.api.features.patients.data

import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.clinics.data.entity.ClinicEntity
import br.com.finlegacy.api.features.maritalStatus.data.entity.MaritalStatusEntity
import br.com.finlegacy.api.features.occupations.data.entity.OccupationEntity
import br.com.finlegacy.api.features.patients.data.entity.PatientEntity
import br.com.finlegacy.api.features.patients.data.mapper.entityToModel
import br.com.finlegacy.api.features.patients.data.table.PatientTable
import br.com.finlegacy.api.features.patients.domain.model.PatientCreate
import br.com.finlegacy.api.features.patients.domain.model.PatientFilter
import br.com.finlegacy.api.features.patients.domain.model.PatientInfo
import br.com.finlegacy.api.features.patients.domain.model.PatientUpdate
import br.com.finlegacy.api.features.patients.domain.repository.PatientRepository
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and

class PatientRepositoryImpl: PatientRepository {

    override suspend fun findByFilter(patientFilter: PatientFilter, clinicId: Long): List<PatientInfo> {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)

            val conditions = mutableListOf<Op<Boolean>>()
            conditions.add(PatientTable.clinic eq clinicId)

            patientFilter.id?.let {
                conditions.add(PatientTable.id eq it)
            }
            patientFilter.cpf?.let {
                conditions.add(PatientTable.cpf like "%${it}%")
            }
            patientFilter.fullName?.let {
                conditions.add(PatientTable.fullName like "%${it}%")
            }
            patientFilter.rg?.let {
                conditions.add(PatientTable.rg eq it)
            }
            patientFilter.email?.let {
                conditions.add(PatientTable.email eq it)
            }
            patientFilter.phoneNumber?.let {
                conditions.add(PatientTable.phoneNumber eq it)
            }

            if (conditions.isNotEmpty()) {
                PatientEntity.find {
                    conditions.reduce { acc, op -> acc and op }
                }.map { item ->
                    item.entityToModel()
                }
            } else {
                PatientEntity.all().map { item ->
                    item.entityToModel()
                }
            }

        }
    }
    override suspend fun findById(id: Long): PatientInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)

            PatientEntity.findById(id)?.entityToModel()
        }
    }
    override suspend fun delete(id: Long): Boolean {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            PatientEntity.findById(id)?.let {
                it.delete()
                true
            } ?: false
        }
    }

    override suspend fun update(patientUpdate: PatientUpdate, clinicId: Long): PatientInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            val clinicEntity = ClinicEntity.findById(clinicId)
                ?: throw ItemNotFoundException("Clinic")

            val maritalStatusEntity = MaritalStatusEntity.findById(patientUpdate.maritalStatusId)
                ?: throw ItemNotFoundException("Marital status")

            val occupationEntity = OccupationEntity.findById(patientUpdate.occupationId)
                ?: throw ItemNotFoundException("Occupation")

            PatientEntity.findByIdAndUpdate(patientUpdate.id) { item ->
                item.fullName = patientUpdate.fullName
                item.fullName = patientUpdate.fullName
                item.cpf = patientUpdate.cpf
                item.rg = patientUpdate.rg
                item.phoneNumber = patientUpdate.phoneNumber
                item.email = patientUpdate.email
                item.birthDate = patientUpdate.birthDate
                item.addressCEP = patientUpdate.address.cep
                item.addressStreet = patientUpdate.address.street
                item.addressNumber = patientUpdate.address.number
                item.addressNeighborhood = patientUpdate.address.neighborhood
                item.addressCity = patientUpdate.address.city
                item.addressState = patientUpdate.address.state
                item.clinic = clinicEntity
                item.maritalStatus = maritalStatusEntity
                item.spouseName = patientUpdate.spouseName
                item.spouseCpf = patientUpdate.spouseCpf
                item.occupation = occupationEntity
                item.income = patientUpdate.income
            }?.entityToModel()
        }
    }

    override suspend fun create(patientCreate: PatientCreate, clinicId: Long): PatientInfo {
        return suspendTransaction {
            val clinicEntity = ClinicEntity.findById(clinicId)
                ?: throw ItemNotFoundException("Clinic")

            val maritalStatusEntity = MaritalStatusEntity.findById(patientCreate.maritalStatusId)
                ?: throw ItemNotFoundException("Marital status")

            val occupationEntity = OccupationEntity.findById(patientCreate.occupationId)
                ?: throw ItemNotFoundException("Occupation")

            PatientEntity.new {
                this.fullName = patientCreate.fullName
                this.cpf = patientCreate.cpf
                this.rg = patientCreate.rg
                this.phoneNumber = patientCreate.phoneNumber
                this.email = patientCreate.email
                this.birthDate = patientCreate.birthDate
                this.addressCEP = patientCreate.address.cep
                this.addressStreet = patientCreate.address.street
                this.addressNumber = patientCreate.address.number
                this.addressNeighborhood = patientCreate.address.neighborhood
                this.addressCity = patientCreate.address.city
                this.addressState = patientCreate.address.state
                this.clinic = clinicEntity
                this.maritalStatus = maritalStatusEntity
                this.spouseName = patientCreate.spouseName
                this.spouseCpf = patientCreate.spouseCpf
                this.occupation = occupationEntity
                this.income = patientCreate.income
            }.entityToModel()

        }
    }
}