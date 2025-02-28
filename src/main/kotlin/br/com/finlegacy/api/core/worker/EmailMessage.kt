package br.com.finlegacy.api.core.worker

import kotlinx.serialization.Serializable

@Serializable
data class EmailMessage(
    val to: String,
    val subject: String,
    val text: String
)
