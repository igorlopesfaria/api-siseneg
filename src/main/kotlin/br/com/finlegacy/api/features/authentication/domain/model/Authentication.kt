package br.com.finlegacy.api.features.authentication.domain.model

import br.com.finlegacy.api.features.users.domain.model.User

data class Authentication(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)