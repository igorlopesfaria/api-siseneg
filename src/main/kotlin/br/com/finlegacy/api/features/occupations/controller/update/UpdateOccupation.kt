package br.com.finlegacy.api.features.occupations.controller.update

import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.extensions.validateRequestField
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.occupations.domain.model.OccupationUpdate
import br.com.finlegacy.api.features.occupations.domain.service.OccupationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject


fun Route.updateOccupation() {
    val service: OccupationService by inject(OccupationService::class.java)

    put ("/v1/maritalStatus"){
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@put

            val maritalStatusUpdate = call.receive<OccupationUpdate>()
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
