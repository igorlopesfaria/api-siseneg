package br.com.finlegacy.api.features.patients.controller.update

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.patients.domain.model.PatientUpdate
import br.com.finlegacy.api.features.patients.domain.service.PatientService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.updatePatient() {

    val service: PatientService by inject(PatientService::class.java)

    put("/v1/patients") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@put
            val patientUpdate = call.receive<PatientUpdate>()

            call.validateRequestField(value = patientUpdate.id.toString(), type = ValidationType.ID).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = patientUpdate.fullName, customFieldMessageError = "Full name", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = patientUpdate.cpf, type = ValidationType.CPF).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = patientUpdate.rg, type = ValidationType.RG).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = patientUpdate.phoneNumber, type = ValidationType.PHONE).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = patientUpdate.email, type = ValidationType.EMAIL).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = patientUpdate.birthDate, type = ValidationType.BIRTH_DATE).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = patientUpdate.maritalStatusId.toString(), customFieldMessageError = "Martial status id", type = ValidationType.ID).let { isValid ->
                if (!isValid) return@put
            }

            when {
                patientUpdate.maritalStatusId == 1L && (patientUpdate.spouseName.isNullOrBlank() || patientUpdate.spouseCpf.isNullOrBlank()) -> {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Patient spouse name and CPF must not be empty"))
                    return@put
                }
                patientUpdate.maritalStatusId == 1L && patientUpdate.spouseCpf?.isValidCPF() == false -> {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Patient spouse CPF must be valid"))
                    return@put
                }
            }
            service.update(patientUpdate, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}