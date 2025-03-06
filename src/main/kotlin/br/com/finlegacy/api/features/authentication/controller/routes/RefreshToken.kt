package br.com.finlegacy.api.features.authentication.controller.routes

import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.authentication.controller.dto.request.RefreshTokenRequest
import br.com.finlegacy.api.features.authentication.controller.mapper.modelToResponse
import br.com.finlegacy.api.features.authentication.domain.service.AuthenticationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.refreshToken() {
    val service: AuthenticationService by inject(AuthenticationService::class.java)
    post("/v1/authentication/refreshToken") {
        try {
            val authenticationRefreshToken = call.receive<RefreshTokenRequest>()

            service.refreshToken(authenticationRefreshToken.refreshToken).handleResult(call) { authentication ->
                call.respond(
                    HttpStatusCode.OK,
                    authentication.modelToResponse()
                )
            }


        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
