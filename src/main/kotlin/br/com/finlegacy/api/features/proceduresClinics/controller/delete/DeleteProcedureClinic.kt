package br.com.finlegacy.api.features.proceduresClinics.controller.delete

import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.extractPathParameter
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.proceduresClinics.domain.service.ProcedureClinicService
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.java.KoinJavaComponent.inject

fun Route.deleteProcedureClinic() {
    val service: ProcedureClinicService by inject(ProcedureClinicService::class.java)

    delete("/v1/procedures/{procedureId}/clinics/{clinicId}") {
        try {

            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@delete
            val procedureId = call.extractPathParameter<Long>(pathParam = "procedureId", type = ValidationType.ID) ?: return@delete
            val clinicId = call.extractPathParameter<Long>(pathParam = "clinicId", type = ValidationType.ID) ?: return@delete

            service.unlinkProcedureClinic(procedureId = procedureId, clinicId = clinicId, uidLogged = uidLogged).handleResult(call) {
                call.respond(HttpStatusCode.OK)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}