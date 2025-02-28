package br.com.finlegacy.api.features.procedures.controller

import br.com.finlegacy.api.features.procedures.controller.update.updateProcedure
import br.com.finlegacy.api.features.procedures.controller.create.createProcedure
import br.com.finlegacy.api.features.procedures.controller.delete.deleteProcedure
import br.com.finlegacy.api.features.procedures.controller.find.findProcedure

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.procedureRoutes() {
    routing {
        route("api/") {
            authenticate {
                deleteProcedure()
                updateProcedure()
                createProcedure()
                findProcedure()
            }
        }
    }
}