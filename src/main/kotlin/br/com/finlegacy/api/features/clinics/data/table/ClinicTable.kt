package br.com.finlegacy.api.features.clinics.data.table

import br.com.finlegacy.api.features.banks.data.table.BankTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption


object ClinicTable : LongIdTable("clinics") {
    val cnpj = varchar("cnpj", 50)
    val name = varchar("name", 250)

    val addressCEP = varchar("address_cep", 50)
    val addressStreet = varchar("address_street", 250).nullable()
    val addressNumber = varchar("address_number", 50).nullable()
    val addressNeighborhood = varchar("address_neighborhood", 250).nullable()
    val addressCity = varchar("address_city", 250)
    val addressState = varchar("address_state", 100)

    val bank = reference("bank_id", BankTable, onDelete = ReferenceOption.CASCADE)  // Foreign key to MaritalStatusTable
    val bankBranchCode = varchar("bank_branch_code", 50)
    val bankAccountNumber = varchar("bank_account_number", 50)

}
