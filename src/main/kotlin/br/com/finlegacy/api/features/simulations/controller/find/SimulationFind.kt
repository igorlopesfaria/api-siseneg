package br.com.finlegacy.api.features.simulations.controller.find

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.simulations.domain.model.SimulationFilter
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

            val patientCpf = call.extractParameter<String?>(
                param = "patientCpf",
                parameterType = ParameterType.QUERY
            )
            val patientName = call.extractParameter<String?>(
                param = "patientName",
                parameterType = ParameterType.QUERY
            )
            val procedureId = call.extractParameter<Long?>(
                param = "procedureId",
                parameterType = ParameterType.QUERY
            )
            val clinicId = call.extractParameter<Long?>(
                param = "clinicId",
                parameterType = ParameterType.QUERY
            )

            val simulationFilter = SimulationFilter(
                patientCpf = patientCpf,
                patientName = patientName,
                procedureId = procedureId,
                clinicId = clinicId
            )

            service.findByFilter(simulationFilter, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

    get("/v1/simulations/{id}") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@get

            val id = call.extractParameter<Long>("id")?: return@get

            service.findById(id, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

}