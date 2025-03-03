package br.com.finlegacy.api.di

import br.com.finlegacy.api.core.worker.emailService.SendGridService
import br.com.finlegacy.api.core.worker.EmailWorker
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Connection
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun applicationModule(applicationConfig: ApplicationConfig) = module {
    // Provide the HttpClient as a singleton
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    // Provide RabbitMQ ConnectionFactory
    single {
        ConnectionFactory().apply {
            host = "localhost" // Replace with your RabbitMQ host
            port = 5672 // Replace with your RabbitMQ port if different
        }
    }

    // Provide RabbitMQ connection
    single<Connection> {
        get<ConnectionFactory>().newConnection()
    }

    // Provide RabbitMQ channel
    single<Channel> { get<Connection>().createChannel() }

    // Retrieve SendGrid API Key from configuration
    single {
        SendGridService(
            httpClient = get(),
            apiKey = applicationConfig.config("ktor.sendgrid").property("apiKey").getString(),
            from = applicationConfig.config("ktor.sendgrid").property("from").getString() // Replace with a verified email in your SendGrid account
        )
    }

    // Provide EmailWorker, which depends on SendGridService and RabbitMQ Channel
    single {
        EmailWorker(
            service = get<SendGridService>(),
            channel = get()
        )
    }
}
