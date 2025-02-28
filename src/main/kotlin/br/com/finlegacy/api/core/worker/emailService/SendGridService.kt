package br.com.finlegacy.api.core.worker.emailService

import br.com.finlegacy.api.core.worker.EmailMessage
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class SendGridService(
    private val httpClient: HttpClient,
    private val apiKey: String,
    private val from: String
) {
    suspend fun sendEmail(emailMessage: EmailMessage): Boolean {
        return try {
            val response = httpClient.post("https://api.sendgrid.com/v3/mail/send") {
                header(HttpHeaders.Authorization, "Bearer $apiKey")
                contentType(ContentType.Application.Json)

                setBody(
                    """
                    {
                        "personalizations": [
                            {
                                "to": [
                                    {
                                        "email": "${emailMessage.to}"
                                    }
                                ],
                                "subject": "${emailMessage.subject}"
                            }
                        ],
                        "from": {
                            "email": "$from"
                        },
                        "content": [
                            {
                                "type": "text/plain",
                                "value": "${emailMessage.text}"
                            }
                        ]
                    }
                    """.trimIndent()
                )
            }
            println("SendGrid response status: ${response.status}")
            response.status.isSuccess()
        } catch (e: Exception) {
            println("Error sending email: ${e.message}")
            false
        }
    }
}
