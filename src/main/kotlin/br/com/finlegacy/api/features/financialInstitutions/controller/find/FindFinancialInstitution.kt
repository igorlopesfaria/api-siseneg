package br.com.finlegacy.api.features.financialInstitutions.controller.find

import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.extractPathParameter
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.financialInstitutions.domain.service.FinancialInstitutionService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.findFinancialInstitution() {
    val service: FinancialInstitutionService by inject(FinancialInstitutionService::class.java)

    get("/v1/financialInstitutions/{id}") {
        try {
            val id = call.extractPathParameter<Long>(pathParam = "id", type = ValidationType.ID) ?: return@get

            service.findById(id).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

    get("/v1/financialInstitutions") {
        try {
            service.findAll().handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
