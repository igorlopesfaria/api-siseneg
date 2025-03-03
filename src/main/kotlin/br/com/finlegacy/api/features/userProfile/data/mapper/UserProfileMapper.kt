package br.com.finlegacy.api.features.userProfile.data.mapper

import br.com.finlegacy.api.features.clinics.data.mapper.entityToModel
import br.com.finlegacy.api.features.userProfile.data.entity.UserProfileEntity
import br.com.finlegacy.api.features.userProfile.domain.model.UserProfileInfo
import br.com.finlegacy.api.features.users.data.entity.UserEntity
import br.com.finlegacy.api.features.users.domain.model.UserInfo

fun UserProfileEntity.entityToModel() = UserProfileInfo(
    id = this.id.value,
    name = this.name,
    isSysAdmin = this.isSysAdmin
)
