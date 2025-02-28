package br.com.finlegacy.api.features.financialInstitutions.data

import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.financialInstitutions.data.entity.FinancialInstitutionEntity
import br.com.finlegacy.api.features.financialInstitutions.data.mapper.entityToModel
import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionCreate
import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionInfo
import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionUpdate
import br.com.finlegacy.api.features.financialInstitutions.domain.repository.FinancialInstitutionRepository
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger

class FinancialInstitutionRepositoryImpl: FinancialInstitutionRepository {

    override suspend fun findById(id: Long): FinancialInstitutionInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            FinancialInstitutionEntity.findById(id)
        }?.entityToModel()
    }

    override suspend fun findAll(): List<FinancialInstitutionInfo> {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            FinancialInstitutionEntity.all().toList().map { item ->
                item.entityToModel()
            }
        }
    }

    override suspend fun delete(id: Long): Boolean {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            FinancialInstitutionEntity.findById(id)?.let {
                it.delete()
                true
            } ?: false
        }
    }

    override suspend fun update(financialInstitutionUpdate: FinancialInstitutionUpdate): FinancialInstitutionInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            FinancialInstitutionEntity.findByIdAndUpdate(financialInstitutionUpdate.id) { item ->
                item.name = financialInstitutionUpdate.name
            }?.entityToModel()
        }
    }

    override suspend fun create(clinicCreate: FinancialInstitutionCreate): FinancialInstitutionInfo {
        return suspendTransaction {
            FinancialInstitutionEntity.new {
                name = clinicCreate.name
            }.entityToModel()
        }
    }
}