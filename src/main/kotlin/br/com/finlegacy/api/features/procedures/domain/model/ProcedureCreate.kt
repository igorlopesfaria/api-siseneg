package br.com.finlegacy.api.features.procedures.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ProcedureCreate (
    val name: String,
)
