package br.com.finlegacy.api.features.address.data

import br.com.finlegacy.api.features.address.data.dto.AddressResponse
import br.com.finlegacy.api.features.address.data.mapper.responseToModel
import br.com.finlegacy.api.features.address.domain.model.AddressInfo
import br.com.finlegacy.api.features.address.domain.repository.AddressRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.supervisorScope

class AddressRepositoryImpl(private val client : HttpClient): AddressRepository {

    override suspend fun findAddressByCEP(cep: String): AddressInfo? = supervisorScope {
        runCatching {
            with(client.get("https://viacep.com.br/ws/$cep/json/")) {
                if (status == HttpStatusCode.OK) body<AddressResponse>().responseToModel() else null
            }
        }.getOrElse {
            null
        }
    }
}