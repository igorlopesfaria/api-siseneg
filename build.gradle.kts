
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val h2_version: String by project
val koin_version: String by project
val postgresql_driver_version: String by project
val mockk_version: String by project
val rabbitmq_version: String by project
val mailgun_version:String by project

plugins {
    kotlin("jvm") version "2.0.20"
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20"

}

group = "br.com.finlagacy-api"
version = "1.0.0"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

sourceSets {
    main {
        resources.srcDir("src/main/resources")
    }
}
repositories {
    mavenCentral() // Add Maven Central
    maven("https://jitpack.io") // Required for Mailgun
}
tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}


dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-client-okhttp")
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation("io.ktor:ktor-serialization-gson")
    implementation("io.ktor:ktor-client-logging-jvm")
    implementation("io.ktor:ktor-server-core:2.x.x")
    implementation("io.ktor:ktor-server-netty:2.x.x")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.x.x") // For JSON serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.x.x") // For serialization library

    implementation ("io.insert-koin:koin-ktor:$koin_version")
    implementation ("io.insert-koin:koin-logger-slf4j:$koin_version")
    implementation("io.ktor:ktor-client-cio-jvm:2.3.12")
    testImplementation("io.insert-koin:koin-test:$koin_version")
    testImplementation("io.insert-koin:koin-test-junit4:$koin_version")

    implementation("org.postgresql:postgresql:$postgresql_driver_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")


    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")

    implementation("org.mindrot:jbcrypt:0.4")

    implementation("com.rabbitmq:amqp-client:$rabbitmq_version")
    implementation("com.mailgun:mailgun-java:$mailgun_version")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")

    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("io.mockk:mockk:$mockk_version") // Check for the latest version
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version") // Adjust version accordingly
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0") // Adjust version accordingly


}
