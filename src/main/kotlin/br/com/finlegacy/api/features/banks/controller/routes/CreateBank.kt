package br.com.finlegacy.api.features.banks.controller.routes

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.features.banks.domain.service.BankService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.banks.controller.dto.mapper.modelToResponse
import br.com.finlegacy.api.features.banks.controller.dto.request.BankCreateRequest

fun Route.createBank() {
    val service: BankService by inject(BankService::class.java)

    post("/v1/banks") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@post
            val bankCreateRequest = call.receive<BankCreateRequest>()

            service.create(bankCreateRequest, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data.modelToResponse())
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}

