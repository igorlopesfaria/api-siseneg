package br.com.finlegacy.api.features.address.domain.repository

import br.com.finlegacy.api.features.address.domain.model.AddressInfo

interface AddressRepository {
    suspend fun findAddressByCEP(cep: String): AddressInfo?
}