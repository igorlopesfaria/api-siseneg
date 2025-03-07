package br.com.finlegacy.api.features.userProfiles.controller.routes

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.userProfiles.domain.service.UserProfileService
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.java.KoinJavaComponent.inject

fun Route.deleteUserProfile() {
    val service: UserProfileService by inject(UserProfileService::class.java)

    delete("/v1/userProfiles/{id}") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@delete
            val id = call.extractParameter<Long>("id")?: return@delete

            service.delete(id, uidLogged).handleResult(call) {
                call.respond(HttpStatusCode.OK)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
