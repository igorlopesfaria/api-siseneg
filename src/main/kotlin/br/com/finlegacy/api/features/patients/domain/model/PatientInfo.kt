package br.com.finlegacy.api.features.patients.domain.model

import br.com.finlegacy.api.features.address.domain.model.Address
import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusInfo
import br.com.finlegacy.api.features.occupations.domain.model.OccupationInfo
import kotlinx.serialization.Serializable

@Serializable
data class PatientInfo(
    val id: Long,
    val fullName: String,
    val cpf: String,
    val rg: String,
    val phoneNumber: String,
    val email: String,
    val birthDate:String,
    val address: Address,
    val maritalStatus: MaritalStatusInfo,
    val income:Double,
    val spouseName: String? = null,
    val spouseCpf: String? = null,
    val occupation: OccupationInfo,
    val clinic: ClinicInfo
)