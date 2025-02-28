package br.com.finlegacy.api.core.exceptions

data class ItemNotFoundException(
    private val field: String? = null
) : Exception() {
    override val message: String = field?.let { "$it not found" } ?: "Item not found"
}
