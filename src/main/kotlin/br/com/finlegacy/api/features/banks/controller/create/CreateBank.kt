package br.com.finlegacy.api.features.banks.controller.create

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.features.banks.domain.service.BankService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.banks.domain.model.BankCreate

fun Route.createBank() {
    val service: BankService by inject(BankService::class.java)

    post("/v1/banks") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@post

            val bankCreate = call.receive<BankCreate>()
            call.validateRequestField(value = bankCreate.name, customFieldMessageError = "name", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@post
            }
            service.create(bankCreate, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}

