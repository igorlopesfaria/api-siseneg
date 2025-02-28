package br.com.finlegacy.api.core.exceptions

data class InvalidFormatException (
    override val message: String? =null
): Exception()