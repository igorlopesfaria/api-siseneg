package br.com.finlegacy.api.features.proceduresClinics.domain.repository

import br.com.finlegacy.api.features.clinics.data.entity.ClinicEntity
import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo
import br.com.finlegacy.api.features.procedures.data.entity.ProcedureEntity
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureInfo
import br.com.finlegacy.api.features.proceduresClinics.domain.model.ProcedureClinicInfo

interface ProcedureClinicRepository {
    suspend fun unlinkProcedureClinic(procedureId: Long, clinicId: Long): Boolean
    suspend fun linkProcedureClinic(procedure: ProcedureInfo, clinic: ClinicInfo): ProcedureClinicInfo
    suspend fun findProceduresByClinicId(clinicId: Long): List<ProcedureClinicInfo>
    suspend fun findClinicByProcedureId(procedureId: Long): List<ProcedureClinicInfo>
}
