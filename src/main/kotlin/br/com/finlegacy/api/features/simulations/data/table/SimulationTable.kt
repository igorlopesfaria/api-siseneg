package br.com.finlegacy.api.features.simulations.data.table

import br.com.finlegacy.api.features.clinics.data.table.ClinicTable
import br.com.finlegacy.api.features.patients.data.table.PatientTable
import br.com.finlegacy.api.features.procedures.data.table.ProcedureTable
import br.com.finlegacy.api.features.users.data.table.UserTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

object SimulationTable : LongIdTable("simulations") {
    val simulatedAmount = double("simulated_amount")
    val installments = integer("installments")
    val userId = reference("user_id", UserTable, onDelete = ReferenceOption.CASCADE)
    val patientId = reference("patient_id", PatientTable, onDelete = ReferenceOption.CASCADE)
    val clinicId = reference("clinic_id", ClinicTable, onDelete = ReferenceOption.CASCADE)
    val procedureId = reference("procedure_id", ProcedureTable, onDelete = ReferenceOption.CASCADE)
    val createdAt = timestamp("created_at").clientDefault { Instant.now() }

}
