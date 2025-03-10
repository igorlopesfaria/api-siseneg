package br.com.finlegacy.api.features.users.domain.model

import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfile

data class User (
    val uid: String,
    val userName: String,
    val clinic: ClinicInfo,
    val userProfile: UserProfile
)