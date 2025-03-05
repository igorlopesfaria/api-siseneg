package br.com.finlegacy.api.features.patients.controller.find

import br.com.finlegacy.api.core.extensions.ParameterType
import br.com.finlegacy.api.core.extensions.extractParameter
import br.com.finlegacy.api.core.extensions.extractUidOrRespondUnauthorized
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.patients.domain.model.PatientFilter
import br.com.finlegacy.api.features.patients.domain.service.PatientService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.findPatient() {
    val service: PatientService by inject(PatientService::class.java)

    get("/v1/patients") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@get
            val cpf = call.extractParameter<String>(
                param = "cpf",
                parameterType = ParameterType.QUERY
            )
            val fullName = call.extractParameter<String>(
                param = "fullName",
                parameterType = ParameterType.QUERY
            )

            service.findByFilter(PatientFilter(cpf= cpf, fullName = fullName), uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

    get("/v1/patients/{id}") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@get
            val id = call.extractParameter<Long>("id")?: return@get

            service.findById(id, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }

}