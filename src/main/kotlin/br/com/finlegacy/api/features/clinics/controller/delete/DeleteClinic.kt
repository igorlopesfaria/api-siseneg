package br.com.finlegacy.api.features.clinics.controller.delete

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.clinics.domain.service.ClinicService
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.java.KoinJavaComponent.inject

fun Route.deleteClinic() {
    val service: ClinicService by inject(ClinicService::class.java)

    delete("/v1/clinics/{id}") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@delete
            val id = call.extractParameter<Long>("id")?: return@delete

            service.delete(id, uidLogged).handleResult(call) {
                call.respond(HttpStatusCode.OK)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
