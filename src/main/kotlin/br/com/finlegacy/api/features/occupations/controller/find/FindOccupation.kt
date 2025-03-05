package br.com.finlegacy.api.features.occupations.controller.find

import br.com.finlegacy.api.core.extensions.extractParameter
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.occupations.domain.service.OccupationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.findOccupation() {
    val service: OccupationService by inject(OccupationService::class.java)

    get("/v1/occupations/{id}") {
        try {
            val id = call.extractParameter<Long>("id")?: return@get

            service.findById(id).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

    get("/v1/occupations") {
        try {
            service.findAll().handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}