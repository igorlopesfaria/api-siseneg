package br.com.finlegacy.api.features.userProfiles.controller

import br.com.finlegacy.api.features.userProfiles.controller.create.createUserProfile
import br.com.finlegacy.api.features.userProfiles.controller.delete.deleteUserProfile
import br.com.finlegacy.api.features.userProfiles.controller.find.findUserProfile
import br.com.finlegacy.api.features.userProfiles.controller.update.updateUserProfile

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
