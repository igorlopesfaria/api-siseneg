package br.com.finlegacy.api.features.banks.controller.update

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.banks.domain.model.BankUpdate
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

            val bankUpdate = call.receive<BankUpdate>()
            call.validateRequestField(value = bankUpdate.name, customFieldMessageError = "name", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@put
            }

            service.update(bankUpdate, uidLogged).handleResult(call) {
                call.respond(HttpStatusCode.OK)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
