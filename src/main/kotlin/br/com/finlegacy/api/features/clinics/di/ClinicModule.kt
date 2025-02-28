package br.com.finlegacy.api.features.clinics.di

import br.com.finlegacy.api.features.clinics.data.ClinicRepositoryImpl
import br.com.finlegacy.api.features.clinics.domain.repository.ClinicRepository
import br.com.finlegacy.api.features.clinics.domain.service.ClinicService
import br.com.finlegacy.api.features.clinics.domain.service.ClinicServiceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val clinicModule = module {
    single { ClinicRepositoryImpl() } bind ClinicRepository::class
    single { ClinicServiceImpl(get(), get()) } bind ClinicService::class
}