package br.com.finlegacy.api.features.financialInstitutions.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FinancialInstitutionUpdate (
    val id: Long,
    val name: String,
)