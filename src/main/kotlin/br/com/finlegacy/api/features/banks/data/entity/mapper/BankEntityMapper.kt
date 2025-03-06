package br.com.finlegacy.api.features.banks.data.entity.mapper

import br.com.finlegacy.api.features.banks.data.entity.BankEntity
import br.com.finlegacy.api.features.banks.domain.model.Bank

fun BankEntity.entityToModel() = Bank(
    this.id.value,
    this.name,
    this.code
)
