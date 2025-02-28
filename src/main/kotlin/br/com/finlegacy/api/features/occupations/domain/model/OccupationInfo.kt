package br.com.finlegacy.api.features.occupations.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class OccupationInfo (
    val id: Long,
    val name: String
)