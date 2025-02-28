package br.com.finlegacy.api.features.financialInstitutions.data.mapper

import br.com.finlegacy.api.features.financialInstitutions.data.entity.FinancialInstitutionEntity
import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionInfo

fun FinancialInstitutionEntity.entityToModel() = FinancialInstitutionInfo(
    this.id.value,
    this.name,
)

