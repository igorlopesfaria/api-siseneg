package br.com.finlegacy.api.features.simulations.data.mapper

import br.com.finlegacy.api.features.clinics.data.mapper.entityToModel
import br.com.finlegacy.api.features.patients.data.mapper.entityToModel
import br.com.finlegacy.api.features.procedures.data.mapper.entityToModel
import br.com.finlegacy.api.features.simulations.data.entity.SimulationEntity
import br.com.finlegacy.api.features.simulations.domain.model.SimulationInfo
import br.com.finlegacy.api.features.users.data.mapper.entityToModel

fun SimulationEntity.entityToModel() = SimulationInfo(
    id =  this.id.value,
    simulatedAmount = this.simulatedAmount,
    installments = this.installments,
    userInfo = this.user.entityToModel(),
    procedureInfo = this.procedure.entityToModel(),
    patient = this.patient.entityToModel(),
    clinicInfo = this.clinic.entityToModel(),
    createdAt = this.createdAt.toString()
)