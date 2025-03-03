package br.com.finlegacy.api.features.clinics.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ClinicCreate (
    val name: String,
    val cnpj: String
)
