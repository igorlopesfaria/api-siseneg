package br.com.finlegacy.api.features.occupations.data.table

import org.jetbrains.exposed.dao.id.LongIdTable


object OccupationTable : LongIdTable("ocupations") {
    val name = varchar("name", 50)
}
