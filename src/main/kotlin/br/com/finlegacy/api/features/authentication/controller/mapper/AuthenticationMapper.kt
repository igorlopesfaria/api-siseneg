package br.com.finlegacy.api.features.authentication.controller.mapper

import br.com.finlegacy.api.features.authentication.controller.dto.response.AuthenticationResponse
import br.com.finlegacy.api.features.authentication.domain.model.Authentication

fun Authentication.modelToResponse() = AuthenticationResponse(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken,
)