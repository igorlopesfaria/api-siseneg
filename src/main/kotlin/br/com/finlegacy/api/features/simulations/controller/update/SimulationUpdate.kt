package br.com.finlegacy.api.features.simulations.controller.update

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.simulations.domain.service.SimulationService
import br.com.finlegacy.api.features.simulations.domain.model.SimulationUpdate
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.updateSimulation() {
    val service: SimulationService by inject(SimulationService::class.java)

    put("/v1/simulation") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@put

            val simulationUpdate = call.receive<SimulationUpdate>()
            call.validateRequestField(value = simulationUpdate.id.toString(), type = ValidationType.ID).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = simulationUpdate.patientId.toString(), customFieldMessageError = "patient id", type = ValidationType.ID).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = simulationUpdate.procedureClinicId.toString(), customFieldMessageError = "procedure clinic id", type = ValidationType.ID).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = simulationUpdate.installments.toString(), customFieldMessageError = "installment", type = ValidationType.ID).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = simulationUpdate.simulatedAmount.toString(), customFieldMessageError = "simulated amount", type = ValidationType.PRICE).let { isValid ->
                if (!isValid) return@put
            }

            service.update(simulationUpdate, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}