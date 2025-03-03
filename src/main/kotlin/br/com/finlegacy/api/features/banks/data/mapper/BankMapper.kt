package br.com.finlegacy.api.features.banks.data.mapper

import br.com.finlegacy.api.features.banks.data.entity.BankEntity
import br.com.finlegacy.api.features.banks.domain.model.BankInfo

fun BankEntity.entityToModel() = BankInfo(
    this.id.value,
    this.name
)
