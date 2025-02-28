package br.com.finlegacy.api.features.simulations.controller.find

import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.extractPathParameter
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.simulations.domain.service.SimulationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.findSimulation() {
    val service: SimulationService by inject(SimulationService::class.java)

    get("/v1/simulations") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@get

            service.findAll(uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

    get("/v1/simulations/{id}") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@get
            val id = call.extractPathParameter<Long>(pathParam = "id", type = ValidationType.ID) ?: return@get

            service.findById(id, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

}