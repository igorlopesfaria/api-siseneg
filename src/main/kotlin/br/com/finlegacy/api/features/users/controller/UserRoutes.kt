package br.com.finlegacy.api.features.users.controller

import br.com.finlegacy.api.features.users.controller.create.createUser
import br.com.finlegacy.api.features.users.controller.delete.deleteUser
import br.com.finlegacy.api.features.users.controller.find.findUser
import br.com.finlegacy.api.features.users.controller.recover.recoverPassword
import br.com.finlegacy.api.features.users.controller.update.updateUser
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.userRoutes() {
    routing {
        route("/api/") {
            recoverPassword()
            authenticate {
                createUser()
                deleteUser()
                updateUser()
                findUser()
            }
        }
    }
}