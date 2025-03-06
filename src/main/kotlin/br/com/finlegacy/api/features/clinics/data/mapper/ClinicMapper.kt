package br.com.finlegacy.api.features.clinics.data.mapper

import br.com.finlegacy.api.features.address.domain.model.Address
import br.com.finlegacy.api.features.banks.data.entity.mapper.entityToModel
import br.com.finlegacy.api.features.clinics.data.entity.ClinicEntity
import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo

fun ClinicEntity.entityToModel() = ClinicInfo(
    id = this.id.value,
    name = this.name,
    cnpj = this.cnpj,
    address = Address(
        cep = this.addressCEP,
        street = this.addressStreet,
        number = this.addressNumber,
        neighborhood = addressNeighborhood,
        city = this.addressCity,
        state = this.addressState

    ),
    bank = this.bank.entityToModel(),
    bankBranchCode = this.bankBranchCode,
    bankAccountNumber = this.bankAccountNumber
)
