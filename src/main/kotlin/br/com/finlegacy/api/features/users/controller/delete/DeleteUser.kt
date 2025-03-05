package br.com.finlegacy.api.features.users.controller.delete

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.users.domain.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.deleteUser() {
    val service: UserService by inject(UserService::class.java)

    delete("/v1/users/{uid}") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@delete
            val uid = call.extractParameter<String>("uid")?: return@delete

            service.delete(uid, uidLogged).handleResult(call) { _ ->
                call.respond(HttpStatusCode.OK)
            }
    } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

}
