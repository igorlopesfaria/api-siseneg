package br.com.finlegacy.api.features.authentication.controller.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest (
    val userName: String,
    val password: String,
)