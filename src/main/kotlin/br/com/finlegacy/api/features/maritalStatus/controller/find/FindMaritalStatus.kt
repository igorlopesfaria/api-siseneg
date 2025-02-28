package br.com.finlegacy.api.features.maritalStatus.controller.find

import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.extractPathParameter
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.maritalStatus.domain.service.MaritalStatusService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.findMaritalStatus() {
    val service: MaritalStatusService by inject(MaritalStatusService::class.java)

    get("/v1/maritalStatus/{id}") {
        try {
            val id = call.extractPathParameter<Long>(pathParam = "id", type = ValidationType.ID) ?: return@get

            service.findById(id).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

    get("/v1/maritalStatus") {
        try {
            service.findAll().handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}