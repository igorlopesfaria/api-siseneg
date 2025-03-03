package br.com.finlegacy.api.features.banks.controller

import br.com.finlegacy.api.features.banks.controller.create.createBank
import br.com.finlegacy.api.features.banks.controller.delete.deleteBank
import br.com.finlegacy.api.features.banks.controller.find.findBank
import br.com.finlegacy.api.features.banks.controller.update.updateBank

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
