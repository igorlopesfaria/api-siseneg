package br.com.finlegacy.api.features.banks.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.banks.domain.model.BankCreate
import br.com.finlegacy.api.features.banks.domain.model.BankInfo
import br.com.finlegacy.api.features.banks.domain.model.BankUpdate

interface BankService {
    suspend fun findById(id: Long, uidLogged: String): Result<BankInfo>
    suspend fun findAll(uidLogged: String): Result<List<BankInfo>>
    suspend fun delete(id: Long, uidLogged: String): Result<Boolean>
    suspend fun update(bankUpdate: BankUpdate, uidLogged: String): Result<BankInfo>
    suspend fun create(bankCreate: BankCreate, uidLogged: String): Result<BankInfo>
}