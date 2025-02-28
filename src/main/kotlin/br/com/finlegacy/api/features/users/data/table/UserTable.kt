package br.com.finlegacy.api.features.users.data.table

import br.com.finlegacy.api.features.clinics.data.table.ClinicTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object UserTable : LongIdTable("users") {
    val userName = varchar("user_name", 255)
    val password = varchar("password", 255)
    val clinicId = reference("clinic_id", ClinicTable, onDelete = ReferenceOption.CASCADE) // One-to-Many relation
    val isAdmin = bool("is_admin").default(false)
    val uid = varchar("uid", 32).uniqueIndex() // UID criptografado (MD5) do usuário
}
