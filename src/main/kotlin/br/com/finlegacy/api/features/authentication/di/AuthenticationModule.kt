package br.com.finlegacy.api.features.authentication.di

import br.com.finlegacy.api.features.authentication.domain.service.AuthenticationService
import br.com.finlegacy.api.features.authentication.domain.service.AuthenticationServiceImpl
import br.com.finlegacy.api.features.users.data.UserRepositoryImpl
import br.com.finlegacy.api.features.users.domain.repository.UserRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val authenticationModule = module {
    single { UserRepositoryImpl() } bind UserRepository::class
    single { AuthenticationServiceImpl(get()) } bind AuthenticationService::class
}