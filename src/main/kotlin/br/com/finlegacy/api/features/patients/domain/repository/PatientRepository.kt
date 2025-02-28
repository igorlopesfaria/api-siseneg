package br.com.finlegacy.api.features.patients.domain.repository

import br.com.finlegacy.api.features.patients.domain.model.PatientCreate
import br.com.finlegacy.api.features.patients.domain.model.PatientFilter
import br.com.finlegacy.api.features.patients.domain.model.PatientInfo
import br.com.finlegacy.api.features.patients.domain.model.PatientUpdate

interface PatientRepository {
    suspend fun findByFilter(patientFilter: PatientFilter, clinicId: Long): List<PatientInfo>
    suspend fun findById(id: Long): PatientInfo?
    suspend fun delete(id: Long): Boolean
    suspend fun update(patientUpdate: PatientUpdate, clinicId: Long): PatientInfo?
    suspend fun create(patientCreate: PatientCreate, clinicId: Long): PatientInfo
}