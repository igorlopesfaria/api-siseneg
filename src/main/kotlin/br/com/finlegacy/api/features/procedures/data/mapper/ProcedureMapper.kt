package br.com.finlegacy.api.features.procedures.data.mapper

import br.com.finlegacy.api.features.procedures.data.entity.ProcedureEntity
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureInfo

fun ProcedureEntity.entityToModel() = ProcedureInfo(
    this.id.value,
    this.name,
)

