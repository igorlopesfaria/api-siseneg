package br.com.finlegacy.api.features.address.controller.find

import br.com.finlegacy.api.core.extensions.ValidationType
import br.com.finlegacy.api.core.extensions.extractPathParameter
import br.com.finlegacy.api.core.extensions.respondUnexpectedError
import br.com.finlegacy.api.core.extensions.validateRequestField
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.address.domain.service.AddressService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.findAddress() {
    val service: AddressService by inject(AddressService::class.java)
    get("/v1/address/{cep}") {
        try {
            val cep: String = call.extractPathParameter<String>(
                pathParam = "cep",
                type = ValidationType.CEP
            ) ?: return@get

            call.validateRequestField(value = cep, type = ValidationType.CEP).let { isValid ->
                if (!isValid) return@get
            }

            service.findAddressByCEP(cep).handleResult(call) {
                call.respond(HttpStatusCode.OK)
            }

        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
