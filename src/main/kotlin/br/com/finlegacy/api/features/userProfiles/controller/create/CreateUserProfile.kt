package br.com.finlegacy.api.features.userProfiles.controller.create

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.features.userProfiles.domain.service.UserProfileService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfileCreate

fun Route.createUserProfile() {
    val service: UserProfileService by inject(UserProfileService::class.java)

    post("/v1/userProfiles") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@post

            val userProfileCreate = call.receive<UserProfileCreate>()
            call.validateRequestField(value = userProfileCreate.name, customFieldMessageError = "name", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = userProfileCreate.isSysAdmin.toString(), customFieldMessageError = "isSysAdmin", type = ValidationType.BOOL).let { isValid ->
                if (!isValid) return@post
            }
            service.create(userProfileCreate, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}

