package cz.cvut.fit.stehlvo2.routing

import cz.cvut.fit.stehlvo2.routing.request.CodeExchangeRequest
import cz.cvut.fit.stehlvo2.routing.response.CodeExchangeResponse
import cz.cvut.fit.stehlvo2.routing.response.UserResponse
import cz.cvut.fit.stehlvo2.service.SessionService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.exchangeCodeRoute() {
    post {
        val exchangeCodeRequest = call.receive<CodeExchangeRequest>()

        val session = SessionService.handleCodeExchange(exchangeCodeRequest.code)
            ?: return@post call.respond(HttpStatusCode.Unauthorized)

        return@post call.respond(
            status = HttpStatusCode.OK,
            message = CodeExchangeResponse(session.sessionToken)
        )
    }
}

fun Route.userRoute() {
    get {
        val sessionToken = call.principal<UserIdPrincipal>()?.name
            ?: return@get call.respond(HttpStatusCode.Unauthorized)

        val session = SessionService.getSession(sessionToken)
            ?: return@get call.respond(HttpStatusCode.Unauthorized)

        return@get call.respond(HttpStatusCode.OK, UserResponse(session.username, session.email))
    }
}