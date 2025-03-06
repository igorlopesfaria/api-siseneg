package br.com.finlegacy.api.features.banks.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.banks.controller.dto.request.BankCreateRequest
import br.com.finlegacy.api.features.banks.domain.model.Bank
import br.com.finlegacy.api.features.banks.controller.dto.request.BankUpdateRequest

interface BankService {
    suspend fun findById(id: Long, uidLogged: String): Result<Bank>
    suspend fun findAll(uidLogged: String): Result<List<Bank>>
    suspend fun delete(id: Long, uidLogged: String): Result<Boolean>
    suspend fun update(bankUpdateRequest: BankUpdateRequest, uidLogged: String): Result<Bank>
    suspend fun create(bankCreateRequest: BankCreateRequest, uidLogged: String): Result<Bank>
}