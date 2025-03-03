package br.com.finlegacy.api.features.banks.domain.repository

import br.com.finlegacy.api.features.banks.domain.model.BankCreate
import br.com.finlegacy.api.features.banks.domain.model.BankInfo
import br.com.finlegacy.api.features.banks.domain.model.BankUpdate

interface BankRepository {
    suspend fun findById(id: Long): BankInfo?
    suspend fun findAll(): List<BankInfo>
    suspend fun delete(id: Long): Boolean
    suspend fun update(bankUpdate: BankUpdate): BankInfo?
    suspend fun create(bankCreate: BankCreate): BankInfo
}