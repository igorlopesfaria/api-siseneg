package br.com.finlegacy.api.features.banks.controller.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class BankUpdateRequest (
    val id: Long,
    val name: String,
    val code: Long,
)
