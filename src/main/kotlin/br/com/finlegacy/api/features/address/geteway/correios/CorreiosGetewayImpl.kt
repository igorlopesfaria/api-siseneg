package br.com.finlegacy.api.features.address.geteway.correios

import br.com.finlegacy.api.features.address.geteway.correios.dto.CorreiosApiResponse
import br.com.finlegacy.api.features.address.geteway.correios.dto.mapper.responseToModel
import br.com.finlegacy.api.features.address.domain.model.Address
import br.com.finlegacy.api.features.address.domain.getway.AddressGetway
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.supervisorScope

class CorreiosGetewayImpl(
    private val client : HttpClient
): AddressGetway {

    override suspend fun findAddressByCEP(cep: String): Address? = supervisorScope {
        runCatching {
            with(client.get("https://viacep.com.br/ws/$cep/json/")) {
                if (status == HttpStatusCode.OK) body<CorreiosApiResponse>().responseToModel() else null
            }
        }.getOrElse {
            null
        }
    }
}