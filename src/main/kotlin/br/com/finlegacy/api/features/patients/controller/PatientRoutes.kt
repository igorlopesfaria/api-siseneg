package br.com.finlegacy.api.features.patients.controller

import br.com.finlegacy.api.features.patients.controller.create.createPatient
import br.com.finlegacy.api.features.patients.controller.delete.deletePatient
import br.com.finlegacy.api.features.patients.controller.find.findPatient
import br.com.finlegacy.api.features.patients.controller.update.updatePatient
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.patientRoutes() {
    routing {
        route("api/") {
            authenticate {
                createPatient()
                updatePatient()
                deletePatient()
                findPatient()
            }
        }
    }
}
