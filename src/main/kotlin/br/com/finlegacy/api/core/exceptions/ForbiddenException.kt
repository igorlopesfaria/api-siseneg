package br.com.finlegacy.api.core.exceptions

data class ForbiddenException (
    override val message: String = "User not allowed to access this resource"
): Exception()
