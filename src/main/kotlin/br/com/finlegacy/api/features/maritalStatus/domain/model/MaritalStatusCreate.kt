package br.com.finlegacy.api.features.maritalStatus.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MaritalStatusCreate (
    val name: String
)