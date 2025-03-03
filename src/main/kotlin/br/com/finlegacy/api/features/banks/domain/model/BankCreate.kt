package br.com.finlegacy.api.features.banks.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BankCreate (
    val name: String,
    val code: String,
)
