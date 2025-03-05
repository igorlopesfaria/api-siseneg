package br.com.finlegacy.api.features.patients.controller.delete

import br.com.finlegacy.api.core.extensions.extractParameter
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.patients.domain.service.PatientService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.deletePatient() {
    val service: PatientService by inject(PatientService::class.java)

    delete("/v1/patients/{id}") {
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
