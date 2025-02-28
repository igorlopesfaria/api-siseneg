package br.com.finlegacy.api.features.procedures.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ProcedureInfo (
    val id: Long,
    val name: String,
)
