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

suspend inline fun <reified T> ApplicationCall.extractParameter(
    param: String,
    customErrorMessage: String? = null,
    parameterType: ParameterType = ParameterType.PATH // Default to path parameter, set to false for query
): T? {
    val value = if (parameterType == ParameterType.PATH) {
        this.parameters[param]?.takeIf { it.isNotBlank() }
    } else {
        this.request.queryParameters[param]?.takeIf { it.isNotBlank() }
    }

    if (value == null) return null // Allow missing parameters

    val parsedValue = when (T::class) {
        Int::class -> value.toIntOrNull()?.takeIf { it > 0 } as? T
        Long::class -> value.toLongOrNull()?.takeIf { it > 0 } as? T
        Float::class -> value.toFloatOrNull()?.takeIf { it > 0 } as? T
        String::class -> value as? T
        else -> null
    }
//    // If parsedValue is null, check if there's validation needed
//    parsedValue?.let {
//        // If validationType is provided, validate accordingly
//        validationType?.let { type ->
//            val isValid = when (type) {
//                NOT_BLANK -> value.isNotBlank()
//                ID -> value.toLongOrNull()?.takeIf { it > 0 } != null
//                EMAIL -> value.isValidEmail()  // Make sure you define this extension function
//                CNPJ -> value.isValidCNPJ()    // Make sure you define this extension function
//                CPF -> value.isValidCPF()      // Make sure you define this extension function
//                PHONE -> value.isValidPhoneNumber()  // Define this extension as well
//                CEP -> value.isValidCEP()      // Define this extension as well
//                RG -> value.isValidRG()        // Define this extension as well
//                BIRTH_DATE -> value.isValidBrazilianBirthDate()  // Define this extension as well
//                PASSWORD -> value.isValidPassword()  // Define this extension as well
//                PRICE -> value.isValidPrice()  // Define this extension as well
//                BOOL -> value.isValidBoolean()  // Define this extension as well
//            }
//
//            if (!isValid) {
//                throw BadRequestException(customErrorMessage ?: "$param must be valid")
//            }
//        }
//    }
    if (parsedValue == null) {
        respondWithError(HttpStatusCode.BadRequest, customErrorMessage ?: "$param must be valid")
    }

    return parsedValue
}



enum class ValidationType {
    NOT_BLANK, ID, EMAIL, CNPJ, CPF, PHONE, CEP, RG, BIRTH_DATE, PASSWORD, PRICE, BOOL
}

enum class ParameterType {
    PATH, QUERY
}


suspend fun ApplicationCall.validateRequestField(
    value: String?,
    customFieldMessageError: String? = null,
    type: ValidationType
): Boolean {
    // Default error message generation based on the type
    val errorMessage = generateErrorMessage(customFieldMessageError, type)

    // Check if the value is null or empty
    if (value.isNullOrEmpty()) {
        respondWithError(HttpStatusCode.BadRequest, errorMessage)
        return false
    }

    // Validate based on the type
    val isValid = when (type) {
        NOT_BLANK -> value.isNotBlank()
        ID -> value.toLongOrNull()?.takeIf { it > 0 } != null
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

    // If not valid, respond with error and return false
    return if (!isValid) {
        respondWithError(HttpStatusCode.BadRequest, errorMessage)
        false
    } else {
        true
    }
}

// Helper function to generate an error message
fun generateErrorMessage(customFieldMessageError: String?, type: ValidationType): String {
    return customFieldMessageError ?: when (type) {
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

// Helper function to respond with error
suspend fun ApplicationCall.respondWithError(statusCode: HttpStatusCode, errorMessage: String) {
    respond(statusCode, mapOf("error" to errorMessage))
}

