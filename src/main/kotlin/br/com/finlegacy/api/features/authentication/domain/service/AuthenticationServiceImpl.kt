package br.com.finlegacy.api.features.authentication.domain.service

import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.extensions.isValidEmail
import br.com.finlegacy.api.core.extensions.toEncrypt
import br.com.finlegacy.api.core.jwt.JwtConfig
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.authentication.domain.model.Authentication
import br.com.finlegacy.api.features.users.domain.repository.UserRepository
import io.ktor.server.plugins.*

class AuthenticationServiceImpl(
    private val userRepository: UserRepository
): AuthenticationService {

    override suspend fun login(userName: String, password: String): Result<Authentication> {
        return runCatching {
            if(!userName.isValidEmail()) throw BadRequestException("Email")

            val user = userRepository.login(userName, password.toEncrypt()) ?: throw ItemNotFoundException("User")

            Authentication(
                accessToken = JwtConfig.generateAccessToken(user.uid),
                refreshToken = JwtConfig.generateRefreshToken(user.uid),
                user = user
            )
        }.fold(onSuccess = { Result.Success(it) }, onFailure = { Result.Failure(it) })
    }

    override suspend fun refreshToken(refreshToken: String): Result<Authentication> {
        return runCatching {

            val uid = JwtConfig.verifier.verify(refreshToken).getClaim("uid").asString()

            if (uid.isNullOrEmpty()) throw BadRequestException("Token")
            val user = userRepository.findByUid(uid) ?: throw ItemNotFoundException("User")

            Authentication(
                accessToken = JwtConfig.generateAccessToken(user.uid),
                refreshToken = JwtConfig.generateRefreshToken(user.uid),
                user = user
            )
        }.fold(onSuccess = { Result.Success(it) }, onFailure = { Result.Failure(it) })
    }
}