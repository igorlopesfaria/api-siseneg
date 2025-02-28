package br.com.finlegacy.api.features.authentication.domain.model

import br.com.finlegacy.api.features.users.domain.model.UserInfo
import kotlinx.serialization.Serializable

@Serializable
data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val user: UserInfo
)