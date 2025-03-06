package br.com.finlegacy.api.features.address.controller.find.mapper

import br.com.finlegacy.api.features.address.controller.find.dto.AddressResponse
import br.com.finlegacy.api.features.address.domain.model.Address

fun Address.modelToDTO() = AddressResponse(
    cep = this.cep,
    street = this.street,
    neighborhood = this.neighborhood,
    city = this.city,
    state = this.state
)