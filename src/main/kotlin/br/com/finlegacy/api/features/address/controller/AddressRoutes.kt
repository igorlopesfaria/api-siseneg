package br.com.finlegacy.api.features.address.controller

import br.com.finlegacy.api.features.address.controller.routes.findAddress
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.addressRoutes() {
    routing {
        route("api/") {
            authenticate {
                findAddress()
            }
        }
    }
}
