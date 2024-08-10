package cz.cvut.fit.stehlvo2

import cz.cvut.fit.stehlvo2.db.DatabaseSingleton
import cz.cvut.fit.stehlvo2.plugins.configureSecurity
import cz.cvut.fit.stehlvo2.plugins.configureSerialization
import cz.cvut.fit.stehlvo2.routing.configureRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

fun main() {
    embeddedServer(Netty, port = 80, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseSingleton.init()
    configureSerialization()

    configureSecurity()
    configureRouting()
}
