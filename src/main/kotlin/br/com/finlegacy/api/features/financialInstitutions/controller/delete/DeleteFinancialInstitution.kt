package br.com.finlegacy.api.features.financialInstitutions.controller.delete

import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.jwt.JwtConfig
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionCreate
import br.com.finlegacy.api.features.financialInstitutions.domain.service.FinancialInstitutionService
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.koin.java.KoinJavaComponent.inject

fun Route.deleteFinancialInstitution() {
    val service: FinancialInstitutionService by inject(FinancialInstitutionService::class.java)

    delete("/v1/financialInstitutions/{id}") {
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
