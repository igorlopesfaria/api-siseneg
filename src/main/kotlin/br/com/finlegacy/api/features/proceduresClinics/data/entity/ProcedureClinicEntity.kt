package br.com.finlegacy.api.features.proceduresClinics.data.entity

import br.com.finlegacy.api.features.clinics.data.entity.ClinicEntity
import br.com.finlegacy.api.features.procedures.data.entity.ProcedureEntity
import br.com.finlegacy.api.features.proceduresClinics.data.table.ProcedureClinicTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ProcedureClinicEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ProcedureClinicEntity>(ProcedureClinicTable)

    var procedure by ProcedureEntity referencedOn ProcedureClinicTable.procedureId
    var clinic by ClinicEntity referencedOn ProcedureClinicTable.clinicId
}
