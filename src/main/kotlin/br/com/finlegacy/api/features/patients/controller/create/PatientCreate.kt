package br.com.finlegacy.api.features.patients.controller.create

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.patients.domain.model.PatientCreate
import br.com.finlegacy.api.features.patients.domain.service.PatientService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.createPatient() {

    val service: PatientService by inject(PatientService::class.java)
    post("/v1/patients") {
        try {
            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@post

            val patientCreate = call.receive<PatientCreate>()
            call.validateRequestField(value = patientCreate.fullName, customFieldMessageError = "Full name", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = patientCreate.cpf, type = ValidationType.CPF).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = patientCreate.rg, type = ValidationType.RG).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = patientCreate.phoneNumber, type = ValidationType.PHONE).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = patientCreate.email, type = ValidationType.EMAIL).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = patientCreate.birthDate, type = ValidationType.BIRTH_DATE).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = patientCreate.clinicId.toString(), customFieldMessageError = "Clinic", type = ValidationType.ID).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = patientCreate.maritalStatusId.toString(), customFieldMessageError = "Marital status", type = ValidationType.ID).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = patientCreate.occupationId.toString(), customFieldMessageError = "Occupation id", type = ValidationType.ID).let { isValid ->
                if (!isValid) return@post
            }
            call.validateRequestField(value = patientCreate.income.toString(), customFieldMessageError = "Income", type = ValidationType.PRICE).let { isValid ->
                if (!isValid) return@post
            }

            when {
                patientCreate.maritalStatusId == 1L && (patientCreate.spouseName.isNullOrBlank() || patientCreate.spouseCpf.isNullOrBlank()) -> {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Patient spouse name and CPF must not be empty for this martial status"))
                    return@post
                }

                patientCreate.maritalStatusId == 1L && patientCreate.spouseCpf?.isValidCPF() == false -> {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Patient spouse CPF must be valid"))
                    return@post
                }
            }

            service.create(patientCreate, uidLogged).handleResult(call) { data ->
                call.respond(HttpStatusCode.Created, data)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
