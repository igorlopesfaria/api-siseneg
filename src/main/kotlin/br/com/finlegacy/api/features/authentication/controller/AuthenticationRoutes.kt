package br.com.finlegacy.api.features.authentication.controller

import br.com.finlegacy.api.features.authentication.controller.login.login
import br.com.finlegacy.api.features.authentication.controller.refreshToken.refreshToken
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.authenticationRoutes() {
    routing {
        route("/api/"){
            login()
            authenticate {
                refreshToken()
            }
        }
    }
}