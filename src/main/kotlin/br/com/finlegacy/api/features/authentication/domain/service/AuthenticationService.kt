package br.com.finlegacy.api.features.authentication.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.authentication.domain.model.Authentication

interface AuthenticationService {
    suspend fun login(userName: String, password: String): Result<Authentication>
    suspend fun refreshToken(refreshToken: String): Result<Authentication>
}