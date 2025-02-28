package br.com.finlegacy.api.features.simulations.controller.create

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.simulations.domain.service.SimulationService
import br.com.finlegacy.api.features.simulations.domain.model.SimulationCreate
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.createSimulation() {
    val service: SimulationService by inject(SimulationService::class.java)

    post("/v1/simulation") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@post

            val simulationCreate = call.receive<SimulationCreate>()
            call.validateRequestField(value = simulationCreate.patientId.toString(), customFieldMessageError = "patient id", type = ValidationType.ID).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = simulationCreate.procedureClinicId.toString(), customFieldMessageError = "procedure clinic id", type = ValidationType.ID).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = simulationCreate.installments.toString(), customFieldMessageError = "installment", type = ValidationType.ID).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = simulationCreate.simulatedAmount.toString(), customFieldMessageError = "simulated amount", type = ValidationType.PRICE).let { isValid ->
                if (!isValid) return@post
            }


            service.create(simulationCreate, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}