package br.com.finlegacy.api.features.financialInstitutions.data.entity

import br.com.finlegacy.api.features.financialInstitutions.data.table.FinancialInstitutionTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class FinancialInstitutionEntity (id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<FinancialInstitutionEntity>(FinancialInstitutionTable)

    var name by FinancialInstitutionTable.name
}
