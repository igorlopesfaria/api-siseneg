package br.com.finlegacy.api.features.proceduresClinics.controller.create

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.proceduresClinics.domain.service.ProcedureClinicService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.createProcedureClinic() {
    val service: ProcedureClinicService by inject(ProcedureClinicService::class.java)

    post  ("/v1/procedures/{procedureId}/clinics/{clinicId}") {
        try {

            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@post
            val procedureId = call.extractParameter<Long>("procedureId")?: return@post
            val clinicId = call.extractParameter<Long>("clinicId")?: return@post

            service.linkProcedureClinic(procedureId = procedureId, clinicId = clinicId, uidLogged = uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}

