package br.com.finlegacy.api.features.userProfiles.controller.routes

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.userProfiles.domain.service.UserProfileService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.findUserProfile() {
    val service: UserProfileService by inject(UserProfileService::class.java)

    authenticate {
        get("/v1/userProfiles/{id}") {
            try {
                val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@get
                val id = call.extractParameter<Long>("id")?: return@get

                service.findById(id, uidLogged).handleResult(call) { data ->
                    call.respond(HttpStatusCode.Created, data)
                }
            } catch (_: Exception) {
                call.respondUnexpectedError()
            }
        }
    }

    get("/v1/userProfiles") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@get

            service.findAll(uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
