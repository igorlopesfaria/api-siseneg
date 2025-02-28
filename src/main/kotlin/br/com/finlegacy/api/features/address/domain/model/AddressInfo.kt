package br.com.finlegacy.api.features.address.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AddressInfo(
    val cep: String,
    val street: String?,
    val neighborhood: String?,
    val city: String,
    val state: String
)
