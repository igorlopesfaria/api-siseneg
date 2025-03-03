package br.com.finlegacy.api.core.extensions

import br.com.finlegacy.api.core.extensions.ValidationType.*
import br.com.finlegacy.api.core.jwt.JwtConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun ApplicationCall.extractUidOrRespondUnauthorized(): String? {
    val authHeader = request.headers["Authorization"]
    return authHeader?.let { JwtConfig.extractUid(it) } ?: run {
        respond(HttpStatusCode.Unauthorized, mapOf("error" to "Unauthorized"))
        null
    }
}
suspend fun ApplicationCall.respondUnexpectedError(message: String = "Unexpected error occurred") {
    respond(HttpStatusCode.InternalServerError, mapOf("error" to message))
}

suspend inline fun <reified T> ApplicationCall.extractPathParameter(pathParam: String, customFieldMessageError: String? = null, type: ValidationType? = null): T? {
    val value = this.parameters[pathParam]

    val errorMessage = if (customFieldMessageError != null) {
        "$customFieldMessageError must be valid"
    } else {
        when (type) {
            ID -> "id must be valid"
            EMAIL -> "email must be valid"
            CNPJ -> "cnpj must be valid"
            CPF -> "cpf must be valid"
            PHONE -> "phoneNumber must be valid"
            CEP -> "cep must be valid"
            RG -> "rg must be valid"
            BIRTH_DATE -> "birth date must be valid"
            PRICE -> "price must be valid"
            else ->  "$pathParam must be valid"
        }
    }

    val parsedValue = when (T::class) {
        Int::class -> value?.toIntOrNull()?.takeIf { it > 0 }
        Long::class -> value?.toLongOrNull()?.takeIf { it > 0 }
        Float::class -> value?.toFloatOrNull()?.takeIf { it > 0 }
        String::class -> value
        else -> null
    }

    if (parsedValue == null) {
        respond(HttpStatusCode.BadRequest, mapOf("error" to errorMessage))
    }

    return parsedValue as? T
}


enum class ValidationType {
    NOT_BLANK, ID, EMAIL, CNPJ, CPF, PHONE, CEP, RG, BIRTH_DATE, PASSWORD, PRICE, BOOL
}


suspend fun ApplicationCall.validateRequestField(value: String?, customFieldMessageError: String? = null, type: ValidationType): Boolean {
    val errorMessage = if (customFieldMessageError != null) {
        "$customFieldMessageError must be valid"
    } else {
        when (type) {
            ID -> "id must be valid"
            EMAIL -> "email must be valid"
            CNPJ -> "cnpj must be valid"
            CPF -> "cpf must be valid"
            PHONE -> "phoneNumber must be valid"
            CEP -> "cep must be valid"
            RG -> "rg must be valid"
            PASSWORD -> "password must be valid"
            BIRTH_DATE -> "birth date must be valid"
            PRICE -> "price must be valid"
            else -> "field must be valid"
        }
    }

    if(value.isNullOrEmpty()) {
        respond(HttpStatusCode.BadRequest, mapOf("error" to customFieldMessageError))
         return false
     }

    val isValid = when (type) {
        NOT_BLANK -> value.isNotBlank()
        ID -> value.toLong() > 0
        EMAIL -> value.isValidEmail()
        CNPJ -> value.isValidCNPJ()
        CPF -> value.isValidCPF()
        PHONE -> value.isValidPhoneNumber()
        RG -> value.isValidRG()
        BIRTH_DATE -> value.isValidBrazilianBirthDate()
        PASSWORD -> value.isValidPassword()
        PRICE -> value.isValidPrice()
        CEP -> value.isValidCEP()
        BOOL -> value.isValidBoolean()
    }

    return if (!isValid) {
        respond(HttpStatusCode.BadRequest, mapOf("error" to errorMessage))
        false
    } else {
        true
    }
}

