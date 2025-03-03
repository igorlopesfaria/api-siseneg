package br.com.finlegacy.api.features.userProfiles.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileInfo (
    val id: Long,
    val name: String,
    val isSysAdmin: Boolean
)