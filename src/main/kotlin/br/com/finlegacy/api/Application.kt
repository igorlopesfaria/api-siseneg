package br.com.finlegacy.api

import br.com.finlegacy.api.core.extensions.configureDatabases
import br.com.finlegacy.api.core.jwt.JwtConfig
import br.com.finlegacy.api.core.worker.EmailWorker
import br.com.finlegacy.api.di.applicationModule
import br.com.finlegacy.api.features.address.di.addressModule
import br.com.finlegacy.api.features.authentication.di.authenticationModule
import br.com.finlegacy.api.features.clinics.di.clinicModule
import br.com.finlegacy.api.features.financialInstitutions.di.financialInstitutionModule
import br.com.finlegacy.api.features.maritalStatus.di.maritalStatusModule
import br.com.finlegacy.api.features.occupations.di.occupationStatusModule
import br.com.finlegacy.api.features.patients.di.patientModule
import br.com.finlegacy.api.features.procedures.di.procedureModule
import br.com.finlegacy.api.features.proceduresClinics.di.procedureClinicModule
import br.com.finlegacy.api.features.simulations.di.simulationModule
import br.com.finlegacy.api.features.users.di.userModule
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8081, module = Application::module).start(wait = true)
}


fun Application.module(testing: Boolean = false) {

    install(Koin) {
        SLF4JLogger()
        modules(
            applicationModule,
            addressModule,
            clinicModule,
            financialInstitutionModule,
            procedureModule,
            procedureClinicModule,
            userModule,
            authenticationModule,
            maritalStatusModule,
            patientModule,
            occupationStatusModule,
            simulationModule
        )
    }
    install(ContentNegotiation) {
        json()
    }

    install(Authentication) {
        jwt {
            verifier(JwtConfig.verifier)
            validate {
                val uid = it.payload.getClaim("uid").asString()
                if (uid != null) {
                    UserIdPrincipal(uid)
                } else {
                    null
                }
            }
        }
    }
    val emailWorker: EmailWorker by inject()
    emailWorker.start()

    configureDatabases(environment.config)
    routes()
}
