package br.com.finlegacy.api.features.banks.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BankInfo (
    val id: Long,
    val name: String,
)