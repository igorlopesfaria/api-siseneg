package br.com.finlegacy.api.features.address.data.mapper

import br.com.finlegacy.api.features.address.data.dto.AddressResponse
import br.com.finlegacy.api.features.address.domain.model.AddressInfo

fun AddressResponse.responseToModel() = AddressInfo(
    cep = this.cep,
    street = this.logradouro,
    number = null,
    neighborhood = this.bairro,
    city = this.localidade,
    state = this.uf
)