package br.com.finlegacy.api.features.financialInstitutions.controller.create

import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.extensions.validateRequestField
import br.com.finlegacy.api.features.financialInstitutions.domain.service.FinancialInstitutionService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionCreate

fun Route.createFinancialInstitution() {
    val service: FinancialInstitutionService by inject(FinancialInstitutionService::class.java)

    post("/v1/financialInstitutions") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@post

            val financialInstitutionCreate = call.receive<FinancialInstitutionCreate>()
            call.validateRequestField(value = financialInstitutionCreate.name, customFieldMessageError = "name", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@post
            }
            service.create(financialInstitutionCreate, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}

