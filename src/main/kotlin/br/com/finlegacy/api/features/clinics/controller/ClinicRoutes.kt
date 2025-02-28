package br.com.finlegacy.api.features.clinics.controller

import br.com.finlegacy.api.features.clinics.controller.create.createClinic
import br.com.finlegacy.api.features.clinics.controller.delete.deleteClinic
import br.com.finlegacy.api.features.clinics.controller.find.findClinic
import br.com.finlegacy.api.features.clinics.controller.update.updateClinic

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.clinicRoutes() {
    routing {
        route("api/") {
            authenticate {
                updateClinic()
                deleteClinic()
                findClinic()
                createClinic()
            }
        }
    }
}
