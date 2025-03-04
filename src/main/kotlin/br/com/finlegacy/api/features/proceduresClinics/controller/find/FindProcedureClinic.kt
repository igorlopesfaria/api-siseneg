package br.com.finlegacy.api.features.proceduresClinics.controller.find

import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.extractPathParameter
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.procedures.domain.service.ProcedureService
import br.com.finlegacy.api.features.proceduresClinics.domain.service.ProcedureClinicService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.findProcedureClinic() {
    val service: ProcedureClinicService by inject(ProcedureClinicService::class.java)
    get("/v1/procedures/{procedureId}/clinics") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@get
            val procedureId = call.extractPathParameter<Long>(pathParam = "procedureId", type = ValidationType.ID) ?: return@get

            service.findClinicByProcedureId(procedureId, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

    get("/v1/clinics/{clinicId}/procedures") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@get
            val clinicId = call.extractPathParameter<Long>(pathParam = "clinicId", type = ValidationType.ID) ?: return@get

            service.findProceduresByClinicId(clinicId, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}