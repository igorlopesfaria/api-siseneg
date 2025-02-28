package br.com.finlegacy.api.features.maritalStatus.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MaritalStatusInfo (
    val id: Long,
    val name: String
)