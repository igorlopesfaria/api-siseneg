package br.com.finlegacy.api.features.financialInstitutions.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FinancialInstitutionCreate (
    val name: String,
)
