package br.com.finlegacy.api.features.address.controller.dto.mapper

import br.com.finlegacy.api.features.address.controller.dto.AddressResponse
import br.com.finlegacy.api.features.address.domain.model.Address

fun Address.modelToDTO() = AddressResponse(
    cep = this.cep,
    street = this.street,
    neighborhood = this.neighborhood,
    city = this.city,
    state = this.state
)