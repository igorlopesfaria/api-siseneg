package br.com.finlegacy.api.core.result

import br.com.finlegacy.api.core.exceptions.BadRequestException
import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemDuplicatedException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.result.Result.Failure
import br.com.finlegacy.api.core.result.Result.Success
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>() // Optional data
    data class Failure(val exception: Throwable) : Result<Nothing>()
}

suspend fun <T> Result<T>.handleResult(call: ApplicationCall, onSuccess: suspend (T) -> Unit) {
    when (this) {
        is Success -> {
            onSuccess(this.data)
        }
        is Failure -> {
            val errorResponse = mapOf("error" to (this.exception.message ?: "Unexpected error occurred"))
            when (this.exception) {
                is BadRequestException -> call.respond(HttpStatusCode.BadRequest, errorResponse)
                is ItemNotFoundException -> call.respond(HttpStatusCode.NotFound, errorResponse)
                is ItemDuplicatedException -> call.respond(HttpStatusCode.Conflict, errorResponse )
                is ForbiddenException -> call.respond(HttpStatusCode.Forbidden, errorResponse)
                else -> call.respond(HttpStatusCode.InternalServerError, errorResponse)
            }
        }
    }
}