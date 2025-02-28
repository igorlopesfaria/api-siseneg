package br.com.finlegacy.api.features.occupations.controller

import br.com.finlegacy.api.features.occupations.controller.create.createOccupation
import br.com.finlegacy.api.features.occupations.controller.delete.deleteOccupation
import br.com.finlegacy.api.features.occupations.controller.find.findOccupation
import br.com.finlegacy.api.features.occupations.controller.update.updateOccupation
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.occupationRoutes() {
    routing {
        route("api/") {
            authenticate {
                createOccupation()
                updateOccupation()
                deleteOccupation()
                findOccupation()
            }
        }
    }
}
