package br.com.finlegacy.api

import br.com.finlegacy.api.features.address.controller.addressRoutes
import br.com.finlegacy.api.features.authentication.controller.authenticationRoutes
import br.com.finlegacy.api.features.clinics.controller.clinicRoutes
import br.com.finlegacy.api.features.financialInstitutions.controller.financialInstitutionsRoutes
import br.com.finlegacy.api.features.maritalStatus.controller.maritalStatusRoutes
import br.com.finlegacy.api.features.occupations.controller.occupationRoutes
import br.com.finlegacy.api.features.patients.controller.patientRoutes
import br.com.finlegacy.api.features.procedures.controller.procedureRoutes
import br.com.finlegacy.api.features.proceduresClinics.controller.procedureClinicRoutes
import br.com.finlegacy.api.features.simulations.controller.simulationRoutes
import br.com.finlegacy.api.features.userProfiles.controller.userProfileRoutes
import br.com.finlegacy.api.features.users.controller.userRoutes
import io.ktor.server.application.*

fun Application.routes() {
    addressRoutes()
    userProfileRoutes()
    userRoutes()
    clinicRoutes()
    procedureRoutes()
    procedureClinicRoutes()
    financialInstitutionsRoutes()
    authenticationRoutes()
    maritalStatusRoutes()
    patientRoutes()
    occupationRoutes()
    simulationRoutes()
}