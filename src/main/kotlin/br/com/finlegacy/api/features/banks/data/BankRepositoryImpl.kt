package br.com.finlegacy.api.features.banks.data

import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.banks.data.entity.BankEntity
import br.com.finlegacy.api.features.banks.data.entity.mapper.entityToModel
import br.com.finlegacy.api.features.banks.controller.dto.request.BankCreateRequest
import br.com.finlegacy.api.features.banks.domain.model.Bank
import br.com.finlegacy.api.features.banks.controller.dto.request.BankUpdateRequest
import br.com.finlegacy.api.features.banks.domain.repository.BankRepository
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger

class BankRepositoryImpl: BankRepository{

    override suspend fun findById(id: Long): Bank? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            BankEntity.findById(id)?.entityToModel()
        }
    }

    override suspend fun findAll(): List<Bank> {
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

    override suspend fun update(bankUpdateRequest: BankUpdateRequest): Bank? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            BankEntity.findByIdAndUpdate(bankUpdateRequest.id) { item ->
                item.name = bankUpdateRequest.name
                item.code = bankUpdateRequest.code
            }?.entityToModel()
        }
    }

    override suspend fun create(bankCreateRequest: BankCreateRequest): Bank {
        return suspendTransaction {
            BankEntity.new {
                this.name = bankCreateRequest.name
                this.code = bankCreateRequest.code
            }.entityToModel()
        }
    }
}