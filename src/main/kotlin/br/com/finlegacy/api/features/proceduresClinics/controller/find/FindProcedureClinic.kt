package br.com.finlegacy.api.features.proceduresClinics.controller.find

import br.com.finlegacy.api.core.extensions.extractParameter
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.result.handleResult
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
            val procedureId = call.extractParameter<Long>("procedureId")?: return@get

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
            val clinicId = call.extractParameter<Long>("clinicId")?: return@get

            service.findProceduresByClinicId(clinicId, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}