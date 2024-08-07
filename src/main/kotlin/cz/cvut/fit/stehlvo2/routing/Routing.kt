package cz.cvut.fit.stehlvo2.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/band") {
            bandRoute()
        }
        route("/place") {
            placeRoute()
        }
        route("/event") {
            eventRoute()
        }
        route("/event/ticket") {
            ticketRoute()
        }
    }

    install(CORS) {
        anyHost()
        allowHeaders { true }

        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)

        allowHeader(HttpHeaders.ContentType) // Allow Content-Type header

        allowCredentials = true
    }
}
