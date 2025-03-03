package br.com.finlegacy.api.features.clinics.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.clinics.domain.model.ClinicCreate
import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo
import br.com.finlegacy.api.features.clinics.domain.model.ClinicUpdate

interface ClinicService {
    suspend fun findById(id: Long, uidLogged: String): Result<ClinicInfo>
    suspend fun findAll(uidLogged: String): Result<List<ClinicInfo>>
    suspend fun delete(id: Long, uidLogged: String): Result<Boolean>
    suspend fun update(clinicUpdate: ClinicUpdate, uidLogged: String): Result<ClinicInfo>
    suspend fun create(clinicCreate: ClinicCreate, uidLogged: String): Result<ClinicInfo>
}