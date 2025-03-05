package br.com.finlegacy.api.features.financialInstitutions.controller.delete

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.financialInstitutions.domain.service.FinancialInstitutionService
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.java.KoinJavaComponent.inject

fun Route.deleteFinancialInstitution() {
    val service: FinancialInstitutionService by inject(FinancialInstitutionService::class.java)

    delete("/v1/financialInstitutions/{id}") {
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
