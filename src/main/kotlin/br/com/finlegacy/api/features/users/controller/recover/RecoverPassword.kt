package br.com.finlegacy.api.features.users.controller.recover

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.users.domain.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.recoverPassword() {
    val service: UserService by inject(UserService::class.java)

    get("/v1/users/recoverPassword/{userName}") {
        try {
            val username = call.extractParameter<String>(
                param = "username",
                customErrorMessage = "Invalid Email format",
            )?: return@get

            service.sendRecoveryPassword(username).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

}