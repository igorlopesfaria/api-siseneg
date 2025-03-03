package br.com.finlegacy.api.features.users.data.mapper

import br.com.finlegacy.api.features.clinics.data.mapper.entityToModel
import br.com.finlegacy.api.features.userProfiles.data.mapper.entityToModel
import br.com.finlegacy.api.features.users.data.entity.UserEntity
import br.com.finlegacy.api.features.users.domain.model.UserInfo

fun UserEntity.entityToModel() = UserInfo(
    uid = this.uid,
    userName = this.userName,
    userProfile = this.userProfile.entityToModel(),
    clinic = this.clinic.entityToModel()
)
