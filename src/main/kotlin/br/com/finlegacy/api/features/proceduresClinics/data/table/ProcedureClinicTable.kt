package br.com.finlegacy.api.features.proceduresClinics.data.table

import br.com.finlegacy.api.features.procedures.data.table.ProcedureTable
import br.com.finlegacy.api.features.clinics.data.table.ClinicTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ProcedureClinicTable : LongIdTable("procedures_clinics") {
    val procedureId = reference("procedure_id", ProcedureTable, onDelete = ReferenceOption.CASCADE)
    val clinicId = reference("clinic_id", ClinicTable, onDelete = ReferenceOption.CASCADE)

    init {
        uniqueIndex(procedureId, clinicId)
    }
}