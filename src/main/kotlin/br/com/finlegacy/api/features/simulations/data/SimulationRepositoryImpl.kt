package br.com.finlegacy.api.features.simulations.data

import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.patients.data.entity.PatientEntity
import br.com.finlegacy.api.features.patients.data.table.PatientTable
import br.com.finlegacy.api.features.procedures.data.entity.ProcedureEntity
import br.com.finlegacy.api.features.procedures.data.table.ProcedureTable
import br.com.finlegacy.api.features.proceduresClinics.data.entity.ProcedureClinicEntity
import br.com.finlegacy.api.features.simulations.data.entity.SimulationEntity
import br.com.finlegacy.api.features.simulations.data.mapper.entityToModel
import br.com.finlegacy.api.features.simulations.domain.model.SimulationCreate
import br.com.finlegacy.api.features.simulations.domain.model.SimulationInfo
import br.com.finlegacy.api.features.simulations.domain.model.SimulationUpdate
import br.com.finlegacy.api.features.simulations.domain.repository.SimulationRepository
import br.com.finlegacy.api.features.users.data.entity.UserEntity
import br.com.finlegacy.api.features.users.data.table.UserTable
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger

class SimulationRepositoryImpl: SimulationRepository {

    override suspend fun findById(id: Long): SimulationInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            SimulationEntity.findById(id)?.entityToModel()
        }
    }

    override suspend fun findAllByClinicId(clinicId: Long): List<SimulationInfo> {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)

            SimulationEntity.find {
                UserTable.clinicId eq clinicId
            }.map { it.entityToModel() }
        }
    }

    override suspend fun findAll(): List<SimulationInfo> {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)

            SimulationEntity.all().map { it.entityToModel() }
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

    override suspend fun update(simulationUpdate: SimulationUpdate, uidLogged: String): SimulationInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)

            val userLogged = UserEntity.find { UserTable.uid eq uidLogged }.singleOrNull()
                ?: throw ItemNotFoundException("User logged")
            val patientEntity = PatientEntity.findById(simulationUpdate.patientId)
                ?: throw ItemNotFoundException("Patient")
            val procedureClinicEntity = ProcedureClinicEntity.findById(simulationUpdate.procedureClinicId)
                ?: throw ItemNotFoundException("Procedure")

            SimulationEntity.findByIdAndUpdate(simulationUpdate.id) { item ->
                item.simulatedAmount = simulationUpdate.simulatedAmount
                item.installments = simulationUpdate.installments
                item.user = userLogged
                item.procedureClinic = procedureClinicEntity
                item.patient = patientEntity
            }?.entityToModel()
        }
    }

    override suspend fun create(simulationCreate: SimulationCreate, uidLogged: String): SimulationInfo {
        return suspendTransaction {

            val userLogged = UserEntity.find { UserTable.uid eq uidLogged }.singleOrNull()
                ?: throw ItemNotFoundException("User logged")
            val patientEntity = PatientEntity.findById(simulationCreate.patientId)
                ?: throw ItemNotFoundException("Patient")
            val procedureClinicEntity = ProcedureClinicEntity.findById(simulationCreate.procedureClinicId)
                ?: throw ItemNotFoundException("Procedure")

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