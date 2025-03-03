package br.com.finlegacy.api.features.userProfiles.di


import br.com.finlegacy.api.features.userProfiles.data.UserProfileRepositoryImpl
import br.com.finlegacy.api.features.userProfiles.domain.repository.UserProfileRepository
import br.com.finlegacy.api.features.userProfiles.domain.service.UserProfileService
import br.com.finlegacy.api.features.userProfiles.domain.service.UserProfileServiceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val userProfileModule = module {
    single { UserProfileRepositoryImpl() } bind UserProfileRepository::class
    single { UserProfileServiceImpl(get(), get()) } bind UserProfileService::class
}