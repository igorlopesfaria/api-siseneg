package br.com.finlegacy.api.features.address.controller.routes

import br.com.finlegacy.api.core.extensions.*
import br.com.finlegacy.api.core.result.handleResult
import br.com.finlegacy.api.features.address.controller.dto.mapper.modelToDTO
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
            val cep = call.extractParameter<String>(param = "cep")?: return@get
            service.findAddressByCEP(cep).handleResult(call) { data ->
                call.respond(HttpStatusCode.OK, data.modelToDTO())
            }
        } catch (_: Exception) {
            call.respondUnexpectedError()
        }
    }
}
