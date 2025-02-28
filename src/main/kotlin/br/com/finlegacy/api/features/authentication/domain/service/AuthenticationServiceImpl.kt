package br.com.finlegacy.api.features.authentication.domain.service

import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.authentication.domain.model.AuthenticationLogin
import br.com.finlegacy.api.features.users.domain.model.UserInfo
import br.com.finlegacy.api.features.users.domain.repository.UserRepository

class AuthenticationServiceImpl(
    private val userRepository: UserRepository
): AuthenticationService {
    override suspend fun login(authenticationLogin: AuthenticationLogin): Result<UserInfo> {
        return runCatching {
            userRepository.login(authenticationLogin)
                ?: throw ItemNotFoundException("User")
        }.fold(onSuccess = { Result.Success(it) }, onFailure = { Result.Failure(it) })
    }
}