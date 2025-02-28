package br.com.finlegacy.api.core.exceptions

data class GenericServerException (
    override val message: String? =null
): Exception()