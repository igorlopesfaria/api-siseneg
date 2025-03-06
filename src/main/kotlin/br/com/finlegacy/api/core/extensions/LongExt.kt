package br.com.finlegacy.api.core.extensions

fun Long.isValidId(): Boolean {
    return this.takeIf { it > 0 } != null
}