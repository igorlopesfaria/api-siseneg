package br.com.finlegacy.api.features.proceduresClinics.data

import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.clinics.data.table.ClinicTable
import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo
import br.com.finlegacy.api.features.procedures.data.table.ProcedureTable
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureInfo
import br.com.finlegacy.api.features.proceduresClinics.data.entity.ProcedureClinicEntity
import br.com.finlegacy.api.features.proceduresClinics.data.mapper.entityToModel
import br.com.finlegacy.api.features.proceduresClinics.data.table.ProcedureClinicTable
import br.com.finlegacy.api.features.proceduresClinics.domain.model.ProcedureClinicInfo
import br.com.finlegacy.api.features.proceduresClinics.domain.repository.ProcedureClinicRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ProcedureClinicRepositoryImpl : ProcedureClinicRepository {

    override suspend fun unlinkProcedureClinic(procedureId: Long, clinicId: Long): Boolean {
        return suspendTransaction {
            val deletedRows = ProcedureClinicTable.deleteWhere {
                (this.clinicId eq clinicId) and (this.procedureId eq procedureId)
            }
            deletedRows > 0 // Return true if any rows were deleted
        }
    }

    override suspend fun linkProcedureClinic(procedure: ProcedureInfo, clinic: ClinicInfo): ProcedureClinicInfo {
        return suspendTransaction {
            val insertedProcedureClinic = ProcedureClinicTable
                .insertAndGetId {
                    it[this.clinicId] = EntityID(clinic.id, ClinicTable)  // Use clinic.id from ClinicInfo
                    it[this.procedureId] = EntityID(procedure.id, ProcedureTable)  // Use procedure.id from ProcedureInfo
                }
            ProcedureClinicInfo(id = insertedProcedureClinic.value, clinic = clinic, procedure = procedure)
        }
    }

    override suspend fun findProceduresByClinicId(clinicId: Long): List<ProcedureInfo> {
        return suspendTransaction {
            ProcedureClinicEntity.find { ProcedureClinicTable.clinicId eq clinicId }
                .map { it.entityToModel().procedure }
        }
    }

    override suspend fun findClinicByProcedureId(procedureId: Long): List<ClinicInfo> {
        return suspendTransaction {
            ProcedureClinicEntity.find {
                ProcedureClinicTable.procedureId eq procedureId
            }.map { it.entityToModel().clinic }
        }
    }
}