package br.com.finlegacy.api.features.users.controller.create

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.users.domain.model.UserCreate
import br.com.finlegacy.api.features.users.domain.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.createUser() {
    val service: UserService by inject(UserService::class.java)

    post("/v1/users") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@post

            val userCreate = call.receive<UserCreate>()
            call.validateRequestField(value = userCreate.userName, type = ValidationType.EMAIL).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = userCreate.password, type = ValidationType.PASSWORD).let { isValid ->
                if (!isValid) return@post
            }
            service.create(userCreate, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
