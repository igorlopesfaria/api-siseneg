package br.com.finlegacy.api.features.users.controller.update

import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemDuplicatedException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.jwt.JwtConfig
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.users.domain.model.UserCreate
import br.com.finlegacy.api.features.users.domain.model.UserUpdate
import br.com.finlegacy.api.features.users.domain.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.updateUser() {
    val service: UserService by inject(UserService::class.java)

    put("/v1/users") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@put

            val userUpdate = call.receive<UserUpdate>()
            call.validateRequestField(value = userUpdate.uid, type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = userUpdate.userName, type = ValidationType.EMAIL).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = userUpdate.password, type = ValidationType.PASSWORD).let { isValid ->
                if (!isValid) return@put
            }
            service.update(userUpdate, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
