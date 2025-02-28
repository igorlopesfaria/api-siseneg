package br.com.finlegacy.api.features.clinics.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ClinicInfo (
    val id: Long,
    val name: String,
    val cnpj: String
)