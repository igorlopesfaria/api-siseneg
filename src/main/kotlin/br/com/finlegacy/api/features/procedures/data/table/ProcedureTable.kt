package br.com.finlegacy.api.features.procedures.data.table

import org.jetbrains.exposed.dao.id.LongIdTable

object ProcedureTable : LongIdTable("procedures") {
    val name = varchar("name", 50)
}
