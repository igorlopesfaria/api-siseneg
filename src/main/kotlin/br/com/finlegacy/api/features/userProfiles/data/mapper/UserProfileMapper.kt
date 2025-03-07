package br.com.finlegacy.api.features.userProfiles.data.mapper

import br.com.finlegacy.api.features.userProfiles.data.entity.UserProfileEntity
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfile

fun UserProfileEntity.entityToModel() = UserProfile(
    id = this.id.value,
    name = this.name,
    isAdmin = this.isAdmin,
    isSysAdmin = this.isSysAdmin
)
