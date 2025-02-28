package br.com.finlegacy.api.features.financialInstitutions.domain.repository

import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionCreate
import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionInfo
import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionUpdate

interface FinancialInstitutionRepository {
    suspend fun findById(id: Long): FinancialInstitutionInfo?
    suspend fun findAll(): List<FinancialInstitutionInfo>
    suspend fun delete(id: Long): Boolean
    suspend fun update(financialInstitutionUpdate: FinancialInstitutionUpdate): FinancialInstitutionInfo?
    suspend fun create(financialInstitutionCreate: FinancialInstitutionCreate): FinancialInstitutionInfo
}