package br.com.finlegacy.api.core.exceptions

data class ItemDuplicatedException(
    private val field: String? = null
) : Exception() {
    override val message: String = field?.let { "$it already registered" } ?: "Item already registered"
}