package br.com.finlegacy.api.features.proceduresClinics.controller.create

import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.extractPathParameter
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
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
            val procedureId = call.extractPathParameter<Long>(pathParam = "procedureId", type = ValidationType.ID) ?: return@post
            val clinicId = call.extractPathParameter<Long>(pathParam = "clinicId", type = ValidationType.ID) ?: return@post

            service.linkProcedureClinic(procedureId = procedureId, clinicId = clinicId, uidLogged = uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}

