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

                item.addressCEP = clinicUpdate.addressInfo.cep
                item.addressStreet = clinicUpdate.addressInfo.street
                item.addressNumber = clinicUpdate.addressInfo.number
                item.addressNeighborhood = clinicUpdate.addressInfo.neighborhood
                item.addressCity = clinicUpdate.addressInfo.city
                item.addressState = clinicUpdate.addressInfo.state

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

                addressCEP = clinicCreate.addressInfo.cep
                addressStreet = clinicCreate.addressInfo.street
                addressNumber = clinicCreate.addressInfo.number
                addressNeighborhood = clinicCreate.addressInfo.neighborhood
                addressCity = clinicCreate.addressInfo.city
                addressState = clinicCreate.addressInfo.state

                bank = matchingBank

                bankBranchCode = clinicCreate.bankBranchCode
                bankAccountNumber = clinicCreate.bankAccountNumber

            }.entityToModel()
        }
    }
}