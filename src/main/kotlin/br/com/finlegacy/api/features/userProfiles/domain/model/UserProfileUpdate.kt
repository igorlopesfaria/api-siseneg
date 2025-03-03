package br.com.finlegacy.api.features.userProfiles.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileUpdate (
    val id: Long,
    val name: String,
    val isAdmin: Boolean,
    val isSysAdmin: Boolean
)