package br.com.finlegacy.api.features.occupations.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class OccupationCreate (
    val name: String
)