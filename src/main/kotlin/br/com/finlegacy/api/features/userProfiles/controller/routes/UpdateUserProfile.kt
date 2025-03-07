package br.com.finlegacy.api.features.userProfiles.controller.routes

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.userProfiles.controller.dto.request.UserProfileUpdateRequest
import br.com.finlegacy.api.features.userProfiles.domain.service.UserProfileService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.updateUserProfile() {
    val service: UserProfileService by inject(UserProfileService::class.java)

    put ("/v1/userProfiles"){
        try {

            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@put

            val userProfileUpdateRequest = call.receive<UserProfileUpdateRequest>()
            call.validateRequestField(value = userProfileUpdateRequest.id.toString(), type = ValidationType.ID).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = userProfileUpdateRequest.name, customFieldMessageError = "name", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = userProfileUpdateRequest.isAdmin.toString(), customFieldMessageError = "isAdmin", type = ValidationType.BOOL).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = userProfileUpdateRequest.isSysAdmin.toString(), customFieldMessageError = "isSysAdmin", type = ValidationType.BOOL).let { isValid ->
                if (!isValid) return@put
            }

            service.update(userProfileUpdateRequest, uidLogged).handleResult(call) {
                call.respond(HttpStatusCode.OK)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
