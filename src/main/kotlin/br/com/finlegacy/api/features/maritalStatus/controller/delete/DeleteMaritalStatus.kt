package br.com.finlegacy.api.features.maritalStatus.controller.delete

import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.extractPathParameter
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.maritalStatus.domain.service.MaritalStatusService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.deleteMaritalStatus() {
    val service: MaritalStatusService by inject(MaritalStatusService::class.java)

    delete("/v1/maritalStatus/{id}") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@delete

            val id = call.extractPathParameter<Long>(pathParam = "id", type = ValidationType.ID) ?: return@delete
            service.delete(id, uidLogged).handleResult(call) { _ ->
                call.respond(HttpStatusCode.OK)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}