package br.com.finlegacy.api.features.financialInstitutions.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionCreate
import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionInfo
import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionUpdate

interface FinancialInstitutionService {
    suspend fun findById(id: Long): Result<FinancialInstitutionInfo>
    suspend fun findAll(): Result<List<FinancialInstitutionInfo>>
    suspend fun delete(id: Long, uidLogged: String): Result<Boolean>
    suspend fun update(financialInstitutionUpdate: FinancialInstitutionUpdate, uidLogged: String): Result<FinancialInstitutionInfo>
    suspend fun create(financialInstitutionCreate: FinancialInstitutionCreate, uidLogged: String): Result<FinancialInstitutionInfo>
}
