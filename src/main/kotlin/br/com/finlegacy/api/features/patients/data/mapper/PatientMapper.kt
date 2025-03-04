package br.com.finlegacy.api.features.patients.data.mapper

import br.com.finlegacy.api.features.address.domain.model.AddressInfo
import br.com.finlegacy.api.features.clinics.data.mapper.entityToModel
import br.com.finlegacy.api.features.maritalStatus.data.mapper.entityToModel
import br.com.finlegacy.api.features.occupations.data.mapper.entityToModel
import br.com.finlegacy.api.features.patients.data.entity.PatientEntity
import br.com.finlegacy.api.features.patients.domain.model.PatientInfo

fun PatientEntity.entityToModel() = PatientInfo(
    id = this.id.value,
    fullName = this.fullName,
    cpf = this.cpf,
    rg = this.rg,
    phoneNumber = this.phoneNumber,
    email = this.email,
    birthDate = this.birthDate,
    addressInfo = AddressInfo(
        this.addressCEP,
        this.addressStreet,
        this.addressNumber,
        this.addressNeighborhood,
        this.addressCity,
        this.addressState
    ),
    maritalStatus = this.maritalStatus.entityToModel(),
    spouseName = this.spouseName,
    spouseCpf = this.spouseCpf,
    occupation = this.occupation.entityToModel(),
    income = this.income,
    clinic = this.clinic.entityToModel()
)
