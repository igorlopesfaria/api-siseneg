package br.com.finlegacy.api.core.extensions

import br.com.finlegacy.api.features.clinics.data.table.ClinicTable
import br.com.finlegacy.api.features.financialInstitutions.data.table.FinancialInstitutionTable
import br.com.finlegacy.api.features.maritalStatus.data.table.MaritalStatusTable
import br.com.finlegacy.api.features.occupations.data.table.OccupationTable
import br.com.finlegacy.api.features.patients.data.table.PatientTable
import br.com.finlegacy.api.features.procedures.data.table.ProcedureTable
import br.com.finlegacy.api.features.proceduresClinics.data.table.ProcedureClinicTable
import br.com.finlegacy.api.features.simulations.data.table.SimulationTable
import br.com.finlegacy.api.features.users.data.table.UserTable
import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases(config: ApplicationConfig) {
    val url = config.property("ktor.storage.jdbcURL").getString()
    val driver =  config.property("ktor.storage.driverClassName").getString()
    val user = config.property("ktor.storage.user").getString()
    val password = config.property("ktor.storage.password").getString()

    Database.connect(url= url, driver = driver, user = user, password = password)

    transaction {
        SchemaUtils.create(ClinicTable)
        SchemaUtils.create(FinancialInstitutionTable)
        SchemaUtils.create(ProcedureTable)
        SchemaUtils.create(ProcedureClinicTable)
        SchemaUtils.create(UserTable)
        SchemaUtils.create(MaritalStatusTable)
        SchemaUtils.create(OccupationTable)
        SchemaUtils.create(PatientTable)
        SchemaUtils.create(SimulationTable)
    }

}
