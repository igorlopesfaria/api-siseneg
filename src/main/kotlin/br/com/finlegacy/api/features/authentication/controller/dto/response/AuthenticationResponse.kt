package br.com.finlegacy.api.features.authentication.controller.dto.response

import kotlinx.serialization.Serializable

@Serializable
class AuthenticationResponse (
    val accessToken: String,
    val refreshToken: String,
)