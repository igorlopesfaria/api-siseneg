package br.com.finlegacy.api.features.clinics.data.mapper

import br.com.finlegacy.api.features.address.domain.model.AddressInfo
import br.com.finlegacy.api.features.banks.data.mapper.entityToModel
import br.com.finlegacy.api.features.clinics.data.entity.ClinicEntity
import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo

fun ClinicEntity.entityToModel() = ClinicInfo(
    id = this.id.value,
    name = this.name,
    cnpj = this.cnpj,
    addressInfo = AddressInfo(
        cep = this.addressCEP,
        street = this.addressStreet,
        number = this.addressNumber,
        neighborhood = addressNeighborhood,
        city = this.addressCity,
        state = this.addressState

    ),
    bankInfo = this.bank.entityToModel(),
    bankBranchCode = this.bankBranchCode,
    bankAccountNumber = this.bankAccountNumber
)
