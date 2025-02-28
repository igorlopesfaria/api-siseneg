package br.com.finlegacy.api.features.occupations.data

import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.occupations.data.mapper.entityToModel
import br.com.finlegacy.api.features.occupations.domain.model.OccupationCreate
import br.com.finlegacy.api.features.occupations.domain.model.OccupationInfo
import br.com.finlegacy.api.features.occupations.domain.model.OccupationUpdate
import br.com.finlegacy.api.features.occupations.domain.repository.OccupationRepository
import br.com.finlegacy.api.features.occupations.data.entity.OccupationEntity
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger

class OccupationRepositoryImpl: OccupationRepository {

    override suspend fun findById(id: Long): OccupationInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            OccupationEntity.findById(id)?.entityToModel()
        }
    }

    override suspend fun findAll(): List<OccupationInfo> {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            OccupationEntity.all().toList().map { item ->
                item.entityToModel()
            }
        }
    }

    override suspend fun delete(id: Long): Boolean {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            OccupationEntity.findById(id)?.let {
                it.delete()
                true
            } ?: false
        }
    }

    override suspend fun update(maritalStatusUpdate: OccupationUpdate): OccupationInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            OccupationEntity.findByIdAndUpdate(maritalStatusUpdate.id) { item ->
                item.name = maritalStatusUpdate.name
            }?.entityToModel()
        }
    }

    override suspend fun create(maritalStatusCreate: OccupationCreate): OccupationInfo {
        return suspendTransaction {
            OccupationEntity.new {
                name = maritalStatusCreate.name
            }.entityToModel()
        }
    }
}