package br.com.finlegacy.api.features.proceduresClinics.controller

import br.com.finlegacy.api.features.proceduresClinics.controller.create.createProcedureClinic
import br.com.finlegacy.api.features.proceduresClinics.controller.delete.deleteProcedureClinic
import br.com.finlegacy.api.features.proceduresClinics.controller.find.findProcedureClinic

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.procedureClinicRoutes() {
    routing {
        route("api/") {
            authenticate {
                deleteProcedureClinic()
                createProcedureClinic()
                findProcedureClinic()
            }
        }
    }
}