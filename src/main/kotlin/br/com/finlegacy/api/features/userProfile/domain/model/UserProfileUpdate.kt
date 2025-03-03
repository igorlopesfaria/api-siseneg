package br.com.finlegacy.api.features.userProfile.domain.model

data class UserProfileUpdate (
    val id: Long,
    val name: String,
    val isSysAdmin: Boolean
)