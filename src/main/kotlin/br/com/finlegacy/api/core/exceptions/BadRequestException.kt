package br.com.finlegacy.api.core.exceptions

data class BadRequestException(
    private val field: String? = null
) : Exception() {
    override val message: String = field?.let { "$it is not in a valid format." } ?: "Item is not in a valid format."
}
