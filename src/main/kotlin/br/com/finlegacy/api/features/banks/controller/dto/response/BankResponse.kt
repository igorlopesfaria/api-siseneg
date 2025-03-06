package br.com.finlegacy.api.features.banks.controller.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class BankResponse (
    val id: Long,
    val name: String,
    val code: Long
)