package br.com.finlegacy.api.features.maritalStatus.data.entity

import br.com.finlegacy.api.features.maritalStatus.data.table.MaritalStatusTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

 class MaritalStatusEntity (id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<MaritalStatusEntity>(MaritalStatusTable)
    var name by MaritalStatusTable.name
 }

