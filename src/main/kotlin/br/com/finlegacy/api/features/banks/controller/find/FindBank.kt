package br.com.finlegacy.api.features.banks.controller.find

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.banks.domain.service.BankService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.findBank() {
    val service: BankService by inject(BankService::class.java)

    authenticate {
        get("/v1/banks/{id}") {
            try {
                val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@get
                val id = call.extractPathParameter<Long>(pathParam = "id", type = ValidationType.ID) ?: return@get

                service.findById(id, uidLogged).handleResult(call) { data ->
                    call.respond(HttpStatusCode.Created, data)
                }
            } catch (_: Exception) {
                call.respondUnexpectedError()
            }
        }
    }

    get("/v1/banks") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@get

            service.findAll(uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
