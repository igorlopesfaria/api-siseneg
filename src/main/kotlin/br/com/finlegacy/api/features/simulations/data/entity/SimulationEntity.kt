package br.com.finlegacy.api.features.simulations.data.entity

import br.com.finlegacy.api.features.clinics.data.entity.ClinicEntity
import br.com.finlegacy.api.features.patients.data.entity.PatientEntity
import br.com.finlegacy.api.features.procedures.data.entity.ProcedureEntity
import br.com.finlegacy.api.features.proceduresClinics.data.entity.ProcedureClinicEntity
import br.com.finlegacy.api.features.simulations.data.table.SimulationTable
import br.com.finlegacy.api.features.users.data.entity.UserEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SimulationEntity (id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<SimulationEntity>(SimulationTable)

    var installments by SimulationTable.installments
    var simulatedAmount by SimulationTable.simulatedAmount
    var patient by PatientEntity referencedOn SimulationTable.patientId
    var procedureClinic by ProcedureClinicEntity referencedOn SimulationTable.procedureClinicId
    var user by UserEntity referencedOn SimulationTable.userId

    var createdAt by SimulationTable.createdAt
}