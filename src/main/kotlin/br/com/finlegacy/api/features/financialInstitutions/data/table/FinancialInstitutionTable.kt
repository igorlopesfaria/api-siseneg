package br.com.finlegacy.api.features.financialInstitutions.data.table

import org.jetbrains.exposed.dao.id.LongIdTable


object FinancialInstitutionTable : LongIdTable("financial_institutions") {
    val name = varchar("name", 50)
}
