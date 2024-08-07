package cz.cvut.fit.stehlvo2

import cz.cvut.fit.stehlvo2.db.DatabaseSingleton
import cz.cvut.fit.stehlvo2.plugins.configureSerialization
import cz.cvut.fit.stehlvo2.routing.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseSingleton.init()
    configureSerialization()

    configureRouting()
}
