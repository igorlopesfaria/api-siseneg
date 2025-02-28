package br.com.finlegacy.api.features.authentication.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.authentication.domain.model.AuthenticationLogin
import br.com.finlegacy.api.features.users.domain.model.UserInfo

interface AuthenticationService {
    suspend fun login(authenticationLogin: AuthenticationLogin): Result<UserInfo>
}