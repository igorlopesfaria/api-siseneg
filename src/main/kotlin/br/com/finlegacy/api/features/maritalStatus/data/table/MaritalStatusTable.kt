package br.com.finlegacy.api.features.maritalStatus.data.table

import org.jetbrains.exposed.dao.id.LongIdTable


object MaritalStatusTable : LongIdTable("marital_status") {
    val name = varchar("name", 50)
}
