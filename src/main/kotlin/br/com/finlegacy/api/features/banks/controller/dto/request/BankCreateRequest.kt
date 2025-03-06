package br.com.finlegacy.api.features.banks.controller.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class BankCreateRequest (
    val name: String,
    val code: Long,
)
