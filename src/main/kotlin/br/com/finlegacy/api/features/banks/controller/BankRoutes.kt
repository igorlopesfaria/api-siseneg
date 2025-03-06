package br.com.finlegacy.api.features.banks.controller

import br.com.finlegacy.api.features.banks.controller.routes.createBank
import br.com.finlegacy.api.features.banks.controller.routes.deleteBank
import br.com.finlegacy.api.features.banks.controller.routes.findBank
import br.com.finlegacy.api.features.banks.controller.routes.updateBank

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.bankRoutes() {
    routing {
        route("api/") {
            authenticate {
                updateBank()
                deleteBank()
                findBank()
                createBank()
            }
        }
    }
}
