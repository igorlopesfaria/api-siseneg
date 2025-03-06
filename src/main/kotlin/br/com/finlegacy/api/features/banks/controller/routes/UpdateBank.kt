package br.com.finlegacy.api.features.banks.controller.routes

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.banks.controller.dto.mapper.modelToResponse
import br.com.finlegacy.api.features.banks.controller.dto.request.BankUpdateRequest
import br.com.finlegacy.api.features.banks.domain.service.BankService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.updateBank() {
    val service: BankService by inject(BankService::class.java)

    put ("/v1/banks"){
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@put
            val bankUpdateRequest = call.receive<BankUpdateRequest>()

            service.update(bankUpdateRequest, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data.modelToResponse())
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
