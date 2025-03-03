package br.com.finlegacy.api.features.users.di

import br.com.finlegacy.api.features.users.data.UserRepositoryImpl
import br.com.finlegacy.api.features.users.domain.repository.UserRepository
import br.com.finlegacy.api.features.users.domain.service.UserService
import br.com.finlegacy.api.features.users.domain.service.UserServiceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule = module {
    single { UserRepositoryImpl() } bind UserRepository::class
    single { UserServiceImpl(get(), get()) } bind UserService::class
}