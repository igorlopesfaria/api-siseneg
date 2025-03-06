package br.com.finlegacy.api.features.address.domain.getway

import br.com.finlegacy.api.features.address.domain.model.Address

interface AddressGetway {
    suspend fun findAddressByCEP(cep: String): Address?
}