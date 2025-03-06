package br.com.finlegacy.api.features.clinics.data

import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.banks.data.entity.BankEntity
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
            val matchingBank = BankEntity.findById(clinicUpdate.bankId )   ?: throw ItemNotFoundException("Bank")

            ClinicEntity.findByIdAndUpdate(clinicUpdate.id) { item ->
                item.name = clinicUpdate.name
                item.cnpj = clinicUpdate.cnpj

                item.addressCEP = clinicUpdate.address.cep
                item.addressStreet = clinicUpdate.address.street
                item.addressNumber = clinicUpdate.address.number
                item.addressNeighborhood = clinicUpdate.address.neighborhood
                item.addressCity = clinicUpdate.address.city
                item.addressState = clinicUpdate.address.state

                item.bank = matchingBank
                item.bankBranchCode = clinicUpdate.bankBranchCode
                item.bankAccountNumber = clinicUpdate.bankAccountNumber
            }?.entityToModel()
        }
    }

    override suspend fun create(clinicCreate: ClinicCreate): ClinicInfo {
        return suspendTransaction {
            val matchingBank = BankEntity.findById(clinicCreate.bankId )   ?: throw ItemNotFoundException("Bank")

            ClinicEntity.new {
                name = clinicCreate.name
                cnpj = clinicCreate.cnpj

                addressCEP = clinicCreate.address.cep
                addressStreet = clinicCreate.address.street
                addressNumber = clinicCreate.address.number
                addressNeighborhood = clinicCreate.address.neighborhood
                addressCity = clinicCreate.address.city
                addressState = clinicCreate.address.state

                bank = matchingBank

                bankBranchCode = clinicCreate.bankBranchCode
                bankAccountNumber = clinicCreate.bankAccountNumber

            }.entityToModel()
        }
    }
}