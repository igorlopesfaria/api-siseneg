package br.com.finlegacy.api.features.users.controller.find

import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.extractPathParameter
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.users.domain.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.findUser() {
    val service: UserService by inject(UserService::class.java)

    get("/v1/users") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@get
            service.findAll(uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

    get("/v1/users/{uid}") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@get
            val uid = call.extractPathParameter<String>(pathParam = "uid", type = ValidationType.NOT_BLANK) ?: return@get

            service.findByUid(uid, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

}
