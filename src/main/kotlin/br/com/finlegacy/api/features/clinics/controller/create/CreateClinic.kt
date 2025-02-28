package br.com.finlegacy.api.features.clinics.controller.create

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.features.clinics.domain.service.ClinicService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.clinics.domain.model.ClinicCreate

fun Route.createClinic() {
    val service: ClinicService by inject(ClinicService::class.java)

    post("/v1/clinics") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@post

            val clinicCreate = call.receive<ClinicCreate>()
            call.validateRequestField(value = clinicCreate.name, customFieldMessageError = "name", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = clinicCreate.cnpj, type = ValidationType.CNPJ).let { isValid ->
                if (!isValid) return@post
            }
            service.create(clinicCreate, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}

