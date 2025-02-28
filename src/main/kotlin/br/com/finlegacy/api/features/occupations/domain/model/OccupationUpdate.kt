package br.com.finlegacy.api.features.occupations.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class OccupationUpdate (
    val id: Long,
    val name: String
)