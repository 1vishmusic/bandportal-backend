package cz.cvut.fit.stehlvo2.plugins

import cz.cvut.fit.stehlvo2.service.SessionService
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureSecurity() {
    install(Authentication) {
        bearer {
            authenticate { bearerTokenCredential ->
                if(SessionService.isSessionTokenValid(bearerTokenCredential.token)) {
                    UserIdPrincipal(bearerTokenCredential.token)
                } else {
                    null
                }
            }
        }
    }
}
