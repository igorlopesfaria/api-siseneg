package br.com.finlegacy.api.features.maritalStatus.data

import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.maritalStatus.data.entity.MaritalStatusEntity
import br.com.finlegacy.api.features.maritalStatus.data.mapper.entityToModel
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusCreate
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusInfo
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusUpdate
import br.com.finlegacy.api.features.maritalStatus.domain.repository.MaritalStatusRepository
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger

class MaritalStatusRepositoryImpl: MaritalStatusRepository {

    override suspend fun findById(id: Long): MaritalStatusInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            MaritalStatusEntity.findById(id)?.entityToModel()
        }
    }

    override suspend fun findAll(): List<MaritalStatusInfo> {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            MaritalStatusEntity.all().toList().map { item ->
                item.entityToModel()
            }
        }
    }

    override suspend fun delete(id: Long): Boolean {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            MaritalStatusEntity.findById(id)?.let {
                it.delete()
                true
            } ?: false
        }
    }

    override suspend fun update(maritalStatusUpdate: MaritalStatusUpdate): MaritalStatusInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            MaritalStatusEntity.findByIdAndUpdate(maritalStatusUpdate.id) { item ->
                item.name = maritalStatusUpdate.name
            }?.entityToModel()
        }
    }

    override suspend fun create(maritalStatusCreate: MaritalStatusCreate): MaritalStatusInfo {
        return suspendTransaction {
            MaritalStatusEntity.new {
                name = maritalStatusCreate.name
            }.entityToModel()
        }
    }
}