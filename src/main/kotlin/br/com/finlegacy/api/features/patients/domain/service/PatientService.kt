package br.com.finlegacy.api.features.patients.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.patients.domain.model.PatientCreate
import br.com.finlegacy.api.features.patients.domain.model.PatientFilter
import br.com.finlegacy.api.features.patients.domain.model.PatientInfo
import br.com.finlegacy.api.features.patients.domain.model.PatientUpdate

interface PatientService {
    suspend fun findById(id: Long, uidLogged: String): Result<PatientInfo>
    suspend fun findByFilter(patientFilter: PatientFilter, uidLogged: String): Result<List<PatientInfo>>
    suspend fun delete(id: Long, uidLogged: String): Result<Boolean>
    suspend fun update(patientUpdate: PatientUpdate, uidLogged: String): Result<PatientInfo>
    suspend fun create(patientCreate: PatientCreate, uidLogged: String): Result<PatientInfo>
}