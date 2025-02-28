package br.com.finlegacy.api.features.maritalStatus.controller

import br.com.finlegacy.api.features.maritalStatus.controller.create.createMaritalStatus
import br.com.finlegacy.api.features.maritalStatus.controller.delete.deleteMaritalStatus
import br.com.finlegacy.api.features.maritalStatus.controller.find.findMaritalStatus
import br.com.finlegacy.api.features.maritalStatus.controller.update.updateMaritalStatus
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.maritalStatusRoutes() {
    routing {
        route("api/") {
            authenticate {
                createMaritalStatus()
                updateMaritalStatus()
                deleteMaritalStatus()
                findMaritalStatus()
            }
        }
    }
}
