package br.com.finlegacy.api.features.proceduresClinics.di

import br.com.finlegacy.api.features.proceduresClinics.data.ProcedureClinicRepositoryImpl
import br.com.finlegacy.api.features.proceduresClinics.domain.repository.ProcedureClinicRepository
import br.com.finlegacy.api.features.proceduresClinics.domain.service.ProcedureClinicService
import br.com.finlegacy.api.features.proceduresClinics.domain.service.ProcedureClinicServiceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val procedureClinicModule = module {
    single { ProcedureClinicRepositoryImpl() } bind ProcedureClinicRepository::class
    single { ProcedureClinicServiceImpl(get(), get(), get(), get()) } bind ProcedureClinicService::class
}