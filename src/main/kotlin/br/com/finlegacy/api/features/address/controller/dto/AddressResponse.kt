package br.com.finlegacy.api.features.address.controller.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse(
    val cep: String,
    val street: String?,
    val neighborhood: String?,
    val city: String,
    val state: String
)