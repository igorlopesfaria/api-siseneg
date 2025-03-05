package br.com.finlegacy.api.features.occupations.controller.delete

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.occupations.domain.service.OccupationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.deleteOccupation() {
    val service: OccupationService by inject(OccupationService::class.java)

    delete("/v1/occupations/{id}") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@delete
            val id = call.extractParameter<Long>("id")?: return@delete

            service.delete(id, uidLogged).handleResult(call) { _ ->
                call.respond(HttpStatusCode.OK)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}