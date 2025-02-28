package br.com.finlegacy.api.features.patients.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PatientFilter (
    val id: Long? = null,
    val cpf: String? = null,
    val rg: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val fullName: String? = null
)