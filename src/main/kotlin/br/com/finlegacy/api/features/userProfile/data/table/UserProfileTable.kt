package br.com.finlegacy.api.features.userProfile.data.table

import org.jetbrains.exposed.dao.id.LongIdTable

object UserProfileTable : LongIdTable("user_profiles") {
    val name = varchar("name", 255)
    val isSysAdmin = bool("is_admin").default(false)
}
