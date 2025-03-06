package br.com.finlegacy.api.features.banks.controller.routes

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.banks.domain.service.BankService
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.java.KoinJavaComponent.inject

fun Route.deleteBank() {
    val service: BankService by inject(BankService::class.java)

    delete("/v1/banks/{id}") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@delete
            val id = call.parameters["id"]?.toLongOrNull()?: run {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Bank ID is required"))
                return@delete
            }

            service.delete(id, uidLogged).handleResult(call) {
                call.respond(HttpStatusCode.OK)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
