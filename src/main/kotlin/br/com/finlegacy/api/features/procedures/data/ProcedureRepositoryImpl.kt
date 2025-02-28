package br.com.finlegacy.api.features.procedures.data

import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.procedures.data.mapper.entityToModel
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureCreate
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureInfo
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureUpdate
import br.com.finlegacy.api.features.procedures.domain.repository.ProcedureRepository
import br.com.finlegacy.api.features.procedures.data.entity.ProcedureEntity
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger

class ProcedureRepositoryImpl: ProcedureRepository {

    override suspend fun findById(id: Long): ProcedureInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            ProcedureEntity.findById(id)?.entityToModel()
        }
    }

    override suspend fun findAll(): List<ProcedureInfo> {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            ProcedureEntity.all().toList().map { item ->
                item.entityToModel()
            }
        }
    }

    override suspend fun delete(id: Long): Boolean {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            ProcedureEntity.findById(id)?.let {
                it.delete()
                true
            } ?: false
        }
    }

    override suspend fun update(procedureUpdate: ProcedureUpdate): ProcedureInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            ProcedureEntity.findByIdAndUpdate(procedureUpdate.id) { item ->
                item.name = procedureUpdate.name
            }?.entityToModel()
        }
    }

    override suspend fun create(procedureCreate: ProcedureCreate): ProcedureInfo {
        return suspendTransaction {
            ProcedureEntity.new {
                name = procedureCreate.name
            }.entityToModel()
        }
    }
}