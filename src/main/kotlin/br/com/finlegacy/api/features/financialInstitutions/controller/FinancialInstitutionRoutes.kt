package br.com.finlegacy.api.features.financialInstitutions.controller

import br.com.finlegacy.api.features.financialInstitutions.controller.create.createFinancialInstitution
import br.com.finlegacy.api.features.financialInstitutions.controller.delete.deleteFinancialInstitution
import br.com.finlegacy.api.features.financialInstitutions.controller.find.findFinancialInstitution
import br.com.finlegacy.api.features.financialInstitutions.controller.update.updateFinancialInstitution
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.financialInstitutionsRoutes() {
    routing {
        route("api/") {
            authenticate {
                createFinancialInstitution()
                updateFinancialInstitution()
                deleteFinancialInstitution()
                findFinancialInstitution()
            }
        }
    }
}
