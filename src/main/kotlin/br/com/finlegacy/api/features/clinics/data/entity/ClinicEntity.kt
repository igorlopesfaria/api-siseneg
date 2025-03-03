package br.com.finlegacy.api.features.clinics.data.entity

import br.com.finlegacy.api.features.banks.data.entity.BankEntity
import br.com.finlegacy.api.features.clinics.data.table.ClinicTable
import br.com.finlegacy.api.features.procedures.data.entity.ProcedureEntity
import br.com.finlegacy.api.features.proceduresClinics.data.table.ProcedureClinicTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ClinicEntity (id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ClinicEntity>(ClinicTable)
    var name by ClinicTable.name
    var cnpj by ClinicTable.cnpj

    var addressCEP by ClinicTable.addressCEP
    var addressStreet by ClinicTable.addressStreet
    var addressNumber by ClinicTable.addressNumber
    var addressNeighborhood by ClinicTable.addressNeighborhood

    var addressCity by ClinicTable.addressCity
    var addressState by ClinicTable.addressState

    var procedures by ProcedureEntity via ProcedureClinicTable // Many-to-many relationship

    var bank by BankEntity referencedOn ClinicTable.bank
    var bankBranchCode by ClinicTable.bankBranchCode
    var bankAccountNumber by ClinicTable.bankAccountNumber
}

