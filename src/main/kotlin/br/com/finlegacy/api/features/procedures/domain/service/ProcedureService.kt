package br.com.finlegacy.api.features.procedures.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureCreate
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureInfo
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureUpdate

interface ProcedureService {
    suspend fun findById(id: Long, uidLogged: String): Result<ProcedureInfo>
    suspend fun findAll(uidLogged: String): Result<List<ProcedureInfo>>
    suspend fun delete(id: Long, uidLogged: String): Result<Boolean>
    suspend fun update(procedureUpdate: ProcedureUpdate, uidLogged: String): Result<ProcedureInfo>
    suspend fun create(procedureCreate: ProcedureCreate, uidLogged: String): Result<ProcedureInfo>
}
