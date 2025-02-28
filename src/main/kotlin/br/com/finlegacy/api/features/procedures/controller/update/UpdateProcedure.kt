package br.com.finlegacy.api.features.procedures.controller.update

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureUpdate
import br.com.finlegacy.api.features.procedures.domain.service.ProcedureService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.updateProcedure() {
    val service: ProcedureService by inject(ProcedureService::class.java)

    put("/v1/procedures") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@put
            val procedureUpdate = call.receive<ProcedureUpdate>()

            call.validateRequestField(value = procedureUpdate.name, customFieldMessageError = "name", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@put
            }
            service.update(procedureUpdate, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

}
