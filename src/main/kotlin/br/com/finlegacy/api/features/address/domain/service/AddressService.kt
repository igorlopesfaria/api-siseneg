package br.com.finlegacy.api.features.address.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.address.domain.model.Address

interface AddressService {
    suspend fun findAddressByCEP(cep: String): Result<Address>
}