package br.com.finlegacy.api.features.procedures.domain.repository

import br.com.finlegacy.api.features.procedures.domain.model.ProcedureCreate
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureInfo
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureUpdate

interface ProcedureRepository {
    suspend fun findById(id: Long): ProcedureInfo?
    suspend fun findAll(): List<ProcedureInfo>
    suspend fun delete(id: Long): Boolean
    suspend fun update(procedureUpdate: ProcedureUpdate): ProcedureInfo?
    suspend fun create(procedureCreate: ProcedureCreate): ProcedureInfo
}