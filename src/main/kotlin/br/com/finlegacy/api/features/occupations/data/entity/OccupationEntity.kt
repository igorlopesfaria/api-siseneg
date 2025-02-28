package br.com.finlegacy.api.features.occupations.data.entity

import br.com.finlegacy.api.features.occupations.data.table.OccupationTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

 class OccupationEntity (id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<OccupationEntity>(OccupationTable)
    var name by OccupationTable.name
 }

