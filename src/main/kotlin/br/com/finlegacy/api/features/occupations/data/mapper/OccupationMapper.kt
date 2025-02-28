package br.com.finlegacy.api.features.occupations.data.mapper

import br.com.finlegacy.api.features.occupations.domain.model.OccupationInfo
import br.com.finlegacy.api.features.occupations.data.entity.OccupationEntity

fun OccupationEntity.entityToModel() = OccupationInfo(
    this.id.value,
    this.name,
)
