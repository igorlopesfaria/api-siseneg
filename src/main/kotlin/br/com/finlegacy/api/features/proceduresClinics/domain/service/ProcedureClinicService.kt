package br.com.finlegacy.api.features.proceduresClinics.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureInfo
import br.com.finlegacy.api.features.proceduresClinics.domain.model.ProcedureClinicInfo

interface ProcedureClinicService {
    suspend fun linkProcedureClinic(procedureId: Long, clinicId: Long, uidLogged:String): Result<ProcedureClinicInfo>
    suspend fun unlinkProcedureClinic(procedureId: Long, clinicId: Long, uidLogged:String): Result<Boolean>
    suspend fun findProceduresByClinicId(clinicId: Long, uidLogged:String): Result<List<ProcedureInfo>>
    suspend fun findClinicByProcedureId(procedureId: Long, uidLogged:String): Result<List<ClinicInfo>>
}