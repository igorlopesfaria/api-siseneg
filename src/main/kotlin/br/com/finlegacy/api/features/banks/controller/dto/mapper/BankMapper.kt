package br.com.finlegacy.api.features.banks.controller.dto.mapper

import br.com.finlegacy.api.features.banks.controller.dto.response.BankResponse
import br.com.finlegacy.api.features.banks.domain.model.Bank

fun Bank.modelToResponse() = BankResponse(
    id = this.id,
    name = this.name,
    code = this.code,
)