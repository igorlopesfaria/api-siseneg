package br.com.finlegacy.api.features.clinics.domain.model

import br.com.finlegacy.api.features.address.domain.model.Address
import br.com.finlegacy.api.features.banks.domain.model.Bank
import kotlinx.serialization.Serializable

data class ClinicInfo (
    val id: Long,
    val name: String,
    val cnpj: String,
    val address: Address,
    val bank: Bank,
    val bankBranchCode: String,
    val bankAccountNumber: String
)