package br.com.finlegacy.api.features.patients.di

import br.com.finlegacy.api.features.patients.data.PatientRepositoryImpl
import br.com.finlegacy.api.features.patients.domain.repository.PatientRepository
import br.com.finlegacy.api.features.patients.domain.service.PatientService
import br.com.finlegacy.api.features.patients.domain.service.PatientServiceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val patientModule = module {
    single { PatientRepositoryImpl() } bind PatientRepository::class
    single { PatientServiceImpl(get(), get(), get()) } bind PatientService::class
}