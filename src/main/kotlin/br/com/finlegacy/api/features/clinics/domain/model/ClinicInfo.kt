package br.com.finlegacy.api.features.clinics.domain.model

import br.com.finlegacy.api.features.address.domain.model.AddressInfo
import br.com.finlegacy.api.features.banks.domain.model.BankInfo
import br.com.finlegacy.api.features.clinics.data.table.ClinicTable.varchar
import kotlinx.serialization.Serializable

@Serializable
data class ClinicInfo (
    val id: Long,
    val name: String,
    val cnpj: String,
    val addressInfo: AddressInfo,
    val bankInfo: BankInfo,
    val bankBranchCode: String,
    val bankAccountNumber: String
)