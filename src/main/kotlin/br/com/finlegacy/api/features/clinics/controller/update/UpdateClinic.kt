package br.com.finlegacy.api.features.clinics.controller.update

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.clinics.domain.model.ClinicUpdate
import br.com.finlegacy.api.features.clinics.domain.service.ClinicService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.updateClinic() {
    val service: ClinicService by inject(ClinicService::class.java)

    put ("/v1/clinics"){
        try {

            val uidLogged = call.extractUidOrRespondUnauthorized() ?: return@put

            val clinicUpdate = call.receive<ClinicUpdate>()
            call.validateRequestField(value = clinicUpdate.name, customFieldMessageError = "name", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = clinicUpdate.cnpj, type = ValidationType.CNPJ).let { isValid ->
                if (!isValid) return@put
            }

            call.validateRequestField(value = clinicUpdate.addressInfo.cep, customFieldMessageError = "address.CEP", type = ValidationType.CEP).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = clinicUpdate.addressInfo.city, customFieldMessageError = "address.city", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = clinicUpdate.addressInfo.state, customFieldMessageError = "address.state", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@put
            }

            call.validateRequestField(value = clinicUpdate.bankId.toString(), customFieldMessageError = "clinicId", type = ValidationType.ID).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = clinicUpdate.bankBranchCode, customFieldMessageError = "clinicBranchCode", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@put
            }
            call.validateRequestField(value = clinicUpdate.bankAccountNumber, customFieldMessageError = "clinicAccountNumber", type = ValidationType.NOT_BLANK).let { isValid ->
                if (!isValid) return@put
            }

            service.update(clinicUpdate, uidLogged).handleResult(call) {
                call.respond(HttpStatusCode.OK)
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
