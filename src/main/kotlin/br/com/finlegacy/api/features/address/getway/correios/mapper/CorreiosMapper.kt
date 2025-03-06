package br.com.finlegacy.api.features.address.getway.correios.mapper

import br.com.finlegacy.api.features.address.getway.correios.dto.CorreiosApiResponse
import br.com.finlegacy.api.features.address.domain.model.Address

fun CorreiosApiResponse.responseToModel() = Address(
    cep = this.cep,
    street = this.logradouro,
    number = null,
    neighborhood = this.bairro,
    city = this.localidade,
    state = this.uf
)