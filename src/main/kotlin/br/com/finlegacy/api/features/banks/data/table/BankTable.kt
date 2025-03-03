package br.com.finlegacy.api.features.banks.data.table

import org.jetbrains.exposed.dao.id.LongIdTable


object BankTable : LongIdTable("banks") {
    val name = varchar("name", 250)
    val code = varchar("code", 50)
}
