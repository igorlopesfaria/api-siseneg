package br.com.finlegacy.api.features.simulations.data

import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.banks.data.entity.BankEntity
import br.com.finlegacy.api.features.banks.data.entity.mapper.entityToModel
import br.com.finlegacy.api.features.clinics.data.entity.ClinicEntity
import br.com.finlegacy.api.features.clinics.data.table.ClinicTable
import br.com.finlegacy.api.features.patients.data.entity.PatientEntity
import br.com.finlegacy.api.features.patients.data.mapper.entityToModel
import br.com.finlegacy.api.features.patients.data.table.PatientTable
import br.com.finlegacy.api.features.procedures.data.entity.ProcedureEntity
import br.com.finlegacy.api.features.procedures.data.table.ProcedureTable
import br.com.finlegacy.api.features.proceduresClinics.data.entity.ProcedureClinicEntity
import br.com.finlegacy.api.features.proceduresClinics.data.table.ProcedureClinicTable
import br.com.finlegacy.api.features.simulations.data.entity.SimulationEntity
import br.com.finlegacy.api.features.simulations.data.mapper.entityToModel
import br.com.finlegacy.api.features.simulations.data.table.SimulationTable
import br.com.finlegacy.api.features.simulations.domain.model.SimulationCreate
import br.com.finlegacy.api.features.simulations.domain.model.SimulationFilter
import br.com.finlegacy.api.features.simulations.domain.model.SimulationInfo
import br.com.finlegacy.api.features.simulations.domain.repository.SimulationRepository
import br.com.finlegacy.api.features.users.data.entity.UserEntity
import br.com.finlegacy.api.features.users.data.table.UserTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like

class SimulationRepositoryImpl: SimulationRepository {

    override suspend fun findByFilter(simulationFilter: SimulationFilter, uidLogged: String): List<SimulationInfo> {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)

            val conditions = mutableListOf<Op<Boolean>>()

            // Add conditions based on the filters provided
            simulationFilter.patientCpf?.let { cpf ->
                conditions.add(PatientTable.cpf like "%${cpf}%")
            }
            simulationFilter.patientName?.let { name ->
                conditions.add(PatientTable.fullName like "%${name}%")
            }
            simulationFilter.procedureId?.let { procedureId ->
                conditions.add(ProcedureClinicTable.procedureId eq procedureId)
            }
            simulationFilter.clinicId?.let { clinicId ->
                conditions.add(ProcedureClinicTable.clinicId eq clinicId)
            }

            // If there are conditions, combine them using `reduce`. Otherwise, use Op.TRUE as a default.
            val query = SimulationTable
                .join(PatientTable, JoinType.INNER, additionalConstraint = {
                    SimulationTable.patientId eq PatientTable.id
                })
                .join(ProcedureClinicTable, JoinType.INNER, additionalConstraint = {
                    SimulationTable.procedureClinicId eq ProcedureClinicTable.id
                })
                .select {
                    // If there are conditions, apply them using `reduce`. If there are no conditions, return `Op.TRUE`.
                    if (conditions.isNotEmpty()) {
                        conditions.reduce { acc, op -> acc and op }
                    } else {
                        Op.TRUE
                    }
                }

            // Map the query result to SimulationInfo model
            query.map {
                val simulationEntity = SimulationEntity.wrapRow(it) // Wrap row into an entity
                simulationEntity.entityToModel() // Convert entity to model
            }
        }
    }



    override suspend fun findById(id: Long): SimulationInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            SimulationEntity.findById(id)?.entityToModel()
        }

    }

    override suspend fun delete(id: Long): Boolean {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            SimulationEntity.findById(id)?.let {
                it.delete()
                true
            } ?: false
        }
    }

    override suspend fun create(simulationCreate: SimulationCreate, uidLogged: String): SimulationInfo {
        return suspendTransaction {

            val userLogged = UserEntity.find { UserTable.uid eq uidLogged }.singleOrNull()
                ?: throw ItemNotFoundException("User logged")
            val patientEntity = PatientEntity.findById(simulationCreate.patientId)
                ?: throw ItemNotFoundException("Patient")
            val procedureClinicEntity = ProcedureClinicEntity.find {
                (ProcedureClinicTable.clinicId eq simulationCreate.clinicId) and
                        (ProcedureClinicTable.procedureId eq simulationCreate.procedureId)
            }.singleOrNull() ?:  throw ItemNotFoundException("Procedure is not related to Clinic and this relation ")

            SimulationEntity.new {
                simulatedAmount = simulationCreate.simulatedAmount
                installments = simulationCreate.installments
                user = userLogged
                procedureClinic = procedureClinicEntity
                patient = patientEntity
            }.entityToModel()
        }
    }
}