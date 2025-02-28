package br.com.finlegacy.api.core.worker

import br.com.finlegacy.api.core.worker.emailService.SendGridService
import com.rabbitmq.client.Channel
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class EmailWorker(
    private val service: SendGridService,
    private val channel: Channel
) {
    private val json = Json { ignoreUnknownKeys = true }

    init {
        // Declare the queue upon initialization
        channel.queueDeclare("emailQueue", true, false, false, null)
    }

    fun start() {
        channel.basicConsume("emailQueue", false, { _, delivery ->
            val messageJson = String(delivery.body, Charsets.UTF_8)
            val emailMessage = parseMessage(messageJson) // Convert JSON to EmailMessage

            if (emailMessage != null) {
                runBlocking {
                    val emailSent = sendEmail(emailMessage)
                    if (emailSent) {
                        channel.basicAck(delivery.envelope.deliveryTag, false)
                    } else {
                        channel.basicNack(delivery.envelope.deliveryTag, false, true)
                    }
                }
            } else {
                println("Failed to parse email message JSON. Acknowledging the message as not processed.")
                channel.basicNack(delivery.envelope.deliveryTag, false, true) // Requeue the message
            }
        }, { _ -> println("Consumer canceled") })
    }

    private suspend fun sendEmail(message: EmailMessage): Boolean {
        return try {
            service.sendEmail(message)
        } catch (e: Exception) {
            println("Failed to send email: ${e.message}")
            false
        }
    }

    private fun parseMessage(messageJson: String): EmailMessage? {
        return try {
            json.decodeFromString<EmailMessage>(messageJson)
        } catch (e: Exception) {
            println("Failed to parse message JSON: ${e.message}")
            null
        }
    }
}
