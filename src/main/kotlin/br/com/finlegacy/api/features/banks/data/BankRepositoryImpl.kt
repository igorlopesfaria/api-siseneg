package br.com.finlegacy.api.features.banks.data

import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.banks.data.entity.BankEntity
import br.com.finlegacy.api.features.banks.data.mapper.entityToModel
import br.com.finlegacy.api.features.banks.domain.model.BankCreate
import br.com.finlegacy.api.features.banks.domain.model.BankInfo
import br.com.finlegacy.api.features.banks.domain.model.BankUpdate
import br.com.finlegacy.api.features.banks.domain.repository.BankRepository
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger

class BankRepositoryImpl: BankRepository{

    override suspend fun findById(id: Long): BankInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            BankEntity.findById(id)?.entityToModel()
        }
    }

    override suspend fun findAll(): List<BankInfo> {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            BankEntity.all().toList().map { item ->
                item.entityToModel()
            }
        }
    }

    override suspend fun delete(id: Long): Boolean {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            BankEntity.findById(id)?.let {
                it.delete()
                true
            } ?: false
        }
    }

    override suspend fun update(bankUpdate: BankUpdate): BankInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            BankEntity.findByIdAndUpdate(bankUpdate.id) { item ->
                item.name = bankUpdate.name
            }?.entityToModel()
        }
    }

    override suspend fun create(bankCreate: BankCreate): BankInfo {
        return suspendTransaction {
            BankEntity.new {
                name = bankCreate.name
            }.entityToModel()
        }
    }
}