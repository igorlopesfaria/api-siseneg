package br.com.finlegacy.api.features.procedures.di

import br.com.finlegacy.api.features.procedures.data.ProcedureRepositoryImpl
import br.com.finlegacy.api.features.procedures.domain.repository.ProcedureRepository
import br.com.finlegacy.api.features.procedures.domain.service.ProcedureService
import br.com.finlegacy.api.features.procedures.domain.service.ProcedureServiceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val procedureModule = module {
    single { ProcedureRepositoryImpl() } bind ProcedureRepository::class
    single { ProcedureServiceImpl(get(), get()) } bind ProcedureService::class
}
