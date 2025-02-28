package br.com.finlegacy.api.features.simulations.controller.delete

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

fun Route.deleteSimulation() {
    val service: SimulationService by inject(SimulationService::class.java)

    delete("/v1/simulations/{id}") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@delete

            val id = call.extractPathParameter<Long>(pathParam = "id", type = ValidationType.ID) ?: return@delete
            service.delete(id, uidLogged).handleResult(call) { _ ->
                call.respond(HttpStatusCode.OK)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
