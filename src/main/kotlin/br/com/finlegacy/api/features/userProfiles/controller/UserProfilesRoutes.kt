package br.com.finlegacy.api.features.userProfiles.controller

import br.com.finlegacy.api.features.userProfiles.controller.routes.createUserProfile
import br.com.finlegacy.api.features.userProfiles.controller.routes.deleteUserProfile
import br.com.finlegacy.api.features.userProfiles.controller.routes.findUserProfile
import br.com.finlegacy.api.features.userProfiles.controller.routes.updateUserProfile

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.userProfileRoutes() {
    routing {
        route("api/") {
            authenticate {
                updateUserProfile()
                deleteUserProfile()
                findUserProfile()
                createUserProfile()
            }
        }
    }
}
