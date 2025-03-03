package br.com.finlegacy.api.features.userProfiles.data.table

import org.jetbrains.exposed.dao.id.LongIdTable

object UserProfileTable : LongIdTable("user_profiles") {
    val name = varchar("name", 50)
    val isAdmin = bool("is_admin").default(false)
    val isSysAdmin = bool("is_sys_admin").default(false)
}
