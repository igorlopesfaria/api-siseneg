package br.com.finlegacy.api.features.maritalStatus.data.mapper

import br.com.finlegacy.api.features.maritalStatus.data.entity.MaritalStatusEntity
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusInfo

fun MaritalStatusEntity.entityToModel() = MaritalStatusInfo(
    this.id.value,
    this.name,
)
