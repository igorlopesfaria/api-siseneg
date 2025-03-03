package br.com.finlegacy.api.features.userProfiles.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileCreate (
    val name: String,
    val isAdmin: Boolean,
    val isSysAdmin: Boolean
)