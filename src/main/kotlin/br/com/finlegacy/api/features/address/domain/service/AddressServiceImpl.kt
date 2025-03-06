package br.com.finlegacy.api.features.address.domain.service

import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.extensions.isValidCEP
import br.com.finlegacy.api.features.address.domain.model.Address
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.address.domain.getway.AddressGetway
import br.com.finlegacy.api.core.exceptions.BadRequestException

class AddressServiceImpl(private val repository: AddressGetway): AddressService {
    override suspend fun findAddressByCEP(cep: String): Result<Address> {
        return runCatching {

            if(!cep.isValidCEP()) throw BadRequestException("CEP")

            repository.findAddressByCEP(cep) ?: throw ItemNotFoundException("Address")
        }.fold(onSuccess = { Result.Success(it) }, onFailure = { Result.Failure(it) })
    }
}