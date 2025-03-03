package br.com.finlegacy.api.features.clinics.data.mapper

import br.com.finlegacy.api.features.clinics.data.entity.ClinicEntity
import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo

fun ClinicEntity.entityToModel() = ClinicInfo(
    this.id.value,
    this.name,
    this.cnpj,
)
