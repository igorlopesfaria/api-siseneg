package br.com.finlegacy.api.features.userProfiles.domain.model

data class UserProfileUpdate (
    val id: Long,
    val name: String,
    val isSysAdmin: Boolean
)