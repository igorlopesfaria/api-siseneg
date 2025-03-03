package br.com.finlegacy.api.features.clinics.data.entity

import br.com.finlegacy.api.features.clinics.data.table.ClinicTable
import br.com.finlegacy.api.features.procedures.data.entity.ProcedureEntity
import br.com.finlegacy.api.features.proceduresClinics.data.table.ProcedureClinicTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ClinicEntity (id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ClinicEntity>(ClinicTable)
    var name by ClinicTable.name
    var cnpj by ClinicTable.cnpj

    var procedures by ProcedureEntity via ProcedureClinicTable // Many-to-many relationship

}

