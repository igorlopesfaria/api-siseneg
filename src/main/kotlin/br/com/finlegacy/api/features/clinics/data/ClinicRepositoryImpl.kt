package br.com.finlegacy.api.features.clinics.data

import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.clinics.data.entity.ClinicEntity
import br.com.finlegacy.api.features.clinics.data.mapper.entityToModel
import br.com.finlegacy.api.features.clinics.domain.model.ClinicCreate
import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo
import br.com.finlegacy.api.features.clinics.domain.model.ClinicUpdate
import br.com.finlegacy.api.features.clinics.domain.repository.ClinicRepository
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger

class ClinicRepositoryImpl: ClinicRepository{

    override suspend fun findById(id: Long): ClinicInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            ClinicEntity.findById(id)?.entityToModel()
        }
    }

    override suspend fun findAll(): List<ClinicInfo> {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            ClinicEntity.all().toList().map { item ->
                item.entityToModel()
            }
        }
    }

    override suspend fun delete(id: Long): Boolean {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            ClinicEntity.findById(id)?.let {
                it.delete()
                true
            } ?: false
        }
    }

    override suspend fun update(clinicUpdate: ClinicUpdate): ClinicInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            ClinicEntity.findByIdAndUpdate(clinicUpdate.id) { item ->
                item.name = clinicUpdate.name
                item.cnpj = clinicUpdate.cnpj
            }?.entityToModel()
        }
    }

    override suspend fun create(clinicCreate: ClinicCreate): ClinicInfo {
        return suspendTransaction {
            ClinicEntity.new {
                name = clinicCreate.name
                cnpj = clinicCreate.cnpj
            }.entityToModel()
        }
    }
}