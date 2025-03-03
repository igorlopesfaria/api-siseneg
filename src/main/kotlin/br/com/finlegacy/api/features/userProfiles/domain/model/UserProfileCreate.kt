package br.com.finlegacy.api.features.userProfiles.domain.model

data class UserProfileCreate  (
    val name: String,
    val isSysAdmin: Boolean
)