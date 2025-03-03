package br.com.finlegacy.api.features.banks.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BankUpdate (
    val id: Long,
    val name: String,
    val code: String,
)
