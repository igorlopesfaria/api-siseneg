package br.com.finlegacy.api.features.clinics.domain.repository

import br.com.finlegacy.api.features.clinics.domain.model.ClinicCreate
import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo
import br.com.finlegacy.api.features.clinics.domain.model.ClinicUpdate

interface ClinicRepository {
    suspend fun findById(id: Long): ClinicInfo?
    suspend fun findAll(): List<ClinicInfo>
    suspend fun delete(id: Long): Boolean
    suspend fun update(clinicUpdate: ClinicUpdate): ClinicInfo?
    suspend fun create(clinicCreate: ClinicCreate): ClinicInfo
}