package br.com.finlegacy.api.features.maritalStatus.controller.update

import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.extensions.validateRequestField
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusUpdate
import br.com.finlegacy.api.features.maritalStatus.domain.service.MaritalStatusService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject


fun Route.updateMaritalStatus() {
    val service: MaritalStatusService by inject(MaritalStatusService::class.java)

    put ("/v1/maritalStatus"){
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@put

            val maritalStatusUpdate = call.receive<MaritalStatusUpdate>()
            call.validateRequestField(value = maritalStatusUpdate.id.toString(), type = ValidationType.ID).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = maritalStatusUpdate.name, customFieldMessageError = "name", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@put
            }
            service.update(maritalStatusUpdate, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
