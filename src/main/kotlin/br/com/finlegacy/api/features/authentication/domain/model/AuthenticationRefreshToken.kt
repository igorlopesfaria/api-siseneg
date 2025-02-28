package br.com.finlegacy.api.features.authentication.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationRefreshToken (
    val token: String,
    val refreshToken: String,
)