package br.com.finlegacy.api.features.maritalStatus.controller.create

import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.extensions.validateRequestField
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusCreate
import br.com.finlegacy.api.features.maritalStatus.domain.service.MaritalStatusService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.createMaritalStatus() {

    val service: MaritalStatusService by inject(MaritalStatusService::class.java)

    post("/v1/maritalStatus") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@post

            val maritalStatusCreate = call.receive<MaritalStatusCreate>()
            call.validateRequestField(value = maritalStatusCreate.name, customFieldMessageError = "name", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@post
            }
            service.create(maritalStatusCreate, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}