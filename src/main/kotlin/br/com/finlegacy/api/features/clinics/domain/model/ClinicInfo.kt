package br.com.finlegacy.api.features.clinics.domain.model

import br.com.finlegacy.api.features.address.domain.model.Address
import br.com.finlegacy.api.features.banks.domain.model.BankInfo
import kotlinx.serialization.Serializable

@Serializable
data class ClinicInfo (
    val id: Long,
    val name: String,
    val cnpj: String,
    val address: Address,
    val bankInfo: BankInfo,
    val bankBranchCode: String,
    val bankAccountNumber: String
)