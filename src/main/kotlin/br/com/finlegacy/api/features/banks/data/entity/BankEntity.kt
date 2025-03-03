package br.com.finlegacy.api.features.banks.data.entity

import br.com.finlegacy.api.features.banks.data.table.BankTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class BankEntity (id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<BankEntity>(BankTable)
    var name by BankTable.name
}

