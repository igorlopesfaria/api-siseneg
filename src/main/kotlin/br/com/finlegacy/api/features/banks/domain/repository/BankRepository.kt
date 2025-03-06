package br.com.finlegacy.api.features.banks.domain.repository

import br.com.finlegacy.api.features.banks.controller.dto.request.BankCreateRequest
import br.com.finlegacy.api.features.banks.domain.model.Bank
import br.com.finlegacy.api.features.banks.controller.dto.request.BankUpdateRequest

interface BankRepository {
    suspend fun findById(id: Long): Bank?
    suspend fun findAll(): List<Bank>
    suspend fun delete(id: Long): Boolean
    suspend fun update(bankUpdateRequest: BankUpdateRequest): Bank?
    suspend fun create(bankCreateRequest: BankCreateRequest): Bank
}