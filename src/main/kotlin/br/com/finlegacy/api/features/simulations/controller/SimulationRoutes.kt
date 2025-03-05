package br.com.finlegacy.api.features.simulations.controller


import br.com.finlegacy.api.features.simulations.controller.create.createSimulation
import br.com.finlegacy.api.features.simulations.controller.delete.deleteSimulation
import br.com.finlegacy.api.features.simulations.controller.find.findSimulation
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.simulationRoutes() {
    routing {
        route("api/") {
            authenticate {
                createSimulation()
                deleteSimulation()
                findSimulation()
            }
        }
    }
}
