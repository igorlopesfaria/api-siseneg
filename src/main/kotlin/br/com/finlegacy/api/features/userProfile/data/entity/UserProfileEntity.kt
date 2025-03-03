package br.com.finlegacy.api.features.userProfile.data.entity

import br.com.finlegacy.api.features.userProfile.data.table.UserProfileTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserProfileEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserProfileEntity>(UserProfileTable)

    var name by UserProfileTable.name
    var isSysAdmin by UserProfileTable.isSysAdmin
}


