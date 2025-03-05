package br.com.finlegacy.api.features.procedures.controller.find

import br.com.finlegacy.api.core.extensions.extractParameter
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.procedures.domain.service.ProcedureService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.findProcedure() {
    val service: ProcedureService by inject(ProcedureService::class.java)

    get("/v1/procedures") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@get
            service.findAll(uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

    get("/v1/procedures/{id}") {
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
