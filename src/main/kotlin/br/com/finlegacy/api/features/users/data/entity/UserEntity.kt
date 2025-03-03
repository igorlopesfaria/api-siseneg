package br.com.finlegacy.api.features.users.data.entity

import br.com.finlegacy.api.features.clinics.data.entity.ClinicEntity
import br.com.finlegacy.api.features.userProfiles.data.entity.UserProfileEntity
import br.com.finlegacy.api.features.users.data.table.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserEntity>(UserTable)

    var uid by UserTable.uid
    var userName by UserTable.userName
    var password by UserTable.password
    var isAdmin by UserTable.isAdmin
    var userProfile by UserProfileEntity referencedOn UserTable.userProfileId  // User belongs to one Clinic
    var clinic by ClinicEntity referencedOn UserTable.clinicId  // User belongs to one Clinic
}
