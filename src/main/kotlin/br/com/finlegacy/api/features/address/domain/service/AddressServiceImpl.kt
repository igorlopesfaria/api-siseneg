package br.com.finlegacy.api.features.address.domain.service

import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.features.address.domain.model.AddressInfo
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.address.domain.repository.AddressRepository

class AddressServiceImpl(private val repository: AddressRepository): AddressService {
    override suspend fun findAddressByCEP(cep: String): Result<AddressInfo> {
        return runCatching {
            repository.findAddressByCEP(cep) ?: throw ItemNotFoundException("Address")
        }.fold(onSuccess = { Result.Success(it) }, onFailure = { Result.Failure(it) })
    }
}