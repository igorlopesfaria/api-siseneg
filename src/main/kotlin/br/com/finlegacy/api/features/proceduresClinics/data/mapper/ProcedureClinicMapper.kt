package br.com.finlegacy.api.features.proceduresClinics.data.mapper

import br.com.finlegacy.api.features.clinics.data.mapper.entityToModel
import br.com.finlegacy.api.features.procedures.data.mapper.entityToModel
import br.com.finlegacy.api.features.proceduresClinics.data.entity.ProcedureClinicEntity
import br.com.finlegacy.api.features.proceduresClinics.domain.model.ProcedureClinicInfo

fun ProcedureClinicEntity.entityToModel() = ProcedureClinicInfo(
    id = this.id.value,
    clinic = this.clinic.entityToModel(),
    procedure = this.procedure.entityToModel()
)

