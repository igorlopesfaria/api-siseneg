package br.com.finlegacy.api.features.userProfiles.controller.routes

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.features.userProfiles.domain.service.UserProfileService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.userProfiles.controller.dto.request.UserProfileCreateRequest

fun Route.createUserProfile() {
    val service: UserProfileService by inject(UserProfileService::class.java)

    post("/v1/userProfiles") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@post
            val userProfileCreateRequest = call.receive<UserProfileCreateRequest>()

            service.create(userProfileCreateRequest, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}

