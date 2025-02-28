package br.com.finlegacy.api.features.procedures.data.entity

import br.com.finlegacy.api.features.procedures.data.table.ProcedureTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ProcedureEntity (id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ProcedureEntity>(ProcedureTable)

    var name by ProcedureTable.name
}