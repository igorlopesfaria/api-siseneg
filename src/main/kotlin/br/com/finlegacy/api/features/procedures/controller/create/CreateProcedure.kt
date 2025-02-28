package br.com.finlegacy.api.features.procedures.controller.create

import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.extensions.validateRequestField
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureCreate
import br.com.finlegacy.api.features.procedures.domain.service.ProcedureService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.createProcedure() {
    val service: ProcedureService by inject(ProcedureService::class.java)

    post("/v1/procedures") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@post

            val procedureCreate = call.receive<ProcedureCreate>()
            call.validateRequestField(value = procedureCreate.name, customFieldMessageError = "name", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@post
            }
            service.create(procedureCreate, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
