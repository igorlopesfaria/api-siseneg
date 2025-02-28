package br.com.finlegacy.api.features.authentication.controller.refreshToken

import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.jwt.JwtConfig
import br.com.finlegacy.api.features.authentication.domain.model.AuthenticationLogin
import br.com.finlegacy.api.features.authentication.domain.model.AuthenticationRefreshToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.refreshToken() {
    post("/v1/authentication/refresh") {
        try {
            val authenticationRefreshToken = call.receive<AuthenticationRefreshToken>()

            val uid = JwtConfig.verifier.verify(authenticationRefreshToken.refreshToken).getClaim("uid").asString()
            val newAccessToken = JwtConfig.generateAccessToken(uid)
            val newRefreshToken = JwtConfig.generateRefreshToken(uid)

            call.respond(HttpStatusCode.OK, mapOf("accessToken" to newAccessToken, "refreshToken" to newRefreshToken))

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
