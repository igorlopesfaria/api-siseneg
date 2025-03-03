package br.com.finlegacy.api.features.clinics.data.table

import org.jetbrains.exposed.dao.id.LongIdTable


object ClinicTable : LongIdTable("clinics") {
    val cnpj = varchar("cnpj", 50)
    val name = varchar("name", 50)
}
