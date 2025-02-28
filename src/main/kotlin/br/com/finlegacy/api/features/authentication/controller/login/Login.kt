package br.com.finlegacy.api.features.authentication.controller.login

import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.extensions.validateRequestField
import br.com.finlegacy.api.core.jwt.JwtConfig
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.authentication.domain.model.AuthInfo
import br.com.finlegacy.api.features.authentication.domain.model.AuthenticationLogin
import br.com.finlegacy.api.features.authentication.domain.service.AuthenticationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.login() {

    val service: AuthenticationService by inject(AuthenticationService::class.java)

    post("/v1/authentication/login") {
        try {
            val authenticationLogin = call.receive<AuthenticationLogin>()
            call.validateRequestField(value = authenticationLogin.userName, type = ValidationType.EMAIL).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = authenticationLogin.password, customFieldMessageError = "password", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@post
            }
            service.login(authenticationLogin).handleResult(call) { data ->
                call.respond(
                    HttpStatusCode.OK,
                    AuthInfo(accessToken = JwtConfig.generateAccessToken(data.uid), refreshToken = JwtConfig.generateRefreshToken(data.uid), user = data)
                )
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
