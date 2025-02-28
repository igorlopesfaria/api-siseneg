package br.com.finlegacy.api.features.address.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse (
    val cep: String,
    val logradouro: String?,
    val complemento: String?,
    val bairro: String?,
    val localidade: String,
    val uf: String,
    val ibge: String?,
    val gia: String?,
    val ddd: String?,
    val siafi: String?
)
