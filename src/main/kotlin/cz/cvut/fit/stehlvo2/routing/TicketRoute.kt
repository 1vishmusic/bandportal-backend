package cz.cvut.fit.stehlvo2.routing

import cz.cvut.fit.stehlvo2.model.Ticket
import cz.cvut.fit.stehlvo2.repository.util.EntityNotFoundException
import cz.cvut.fit.stehlvo2.routing.request.TicketRequest
import cz.cvut.fit.stehlvo2.routing.response.TicketResponse
import cz.cvut.fit.stehlvo2.service.TicketService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.ticketRoute() {
    post {
        val ticketRequest = call.receive<TicketRequest>()
        val createdTicket = TicketService.create(ticketRequest.toModel())
            ?: return@post call.respond(HttpStatusCode.BadRequest)

        call.respond(
            message = createdTicket.toResponse(),
            status = HttpStatusCode.Created
        )
    }

    get {
        val tickets = TicketService.readAll()

        call.respond(
            message = tickets.map(Ticket::toResponse),
            status = HttpStatusCode.OK
        )
    }

    get("/{id}") {
        val id: Long = call.parameters["id"]?.toLong() ?:
        return@get call.respond(HttpStatusCode.BadRequest)

        val foundTicket = TicketService.readById(id) ?:
        return@get call.respond(HttpStatusCode.NotFound)

        call.respond(
            message = foundTicket.toResponse(),
            status = HttpStatusCode.Created
        )
    }

    put("/{id}") {
        val id: Long = call.parameters["id"]?.toLong()
            ?: return@put call.respond(HttpStatusCode.BadRequest)

        val ticketRequest = call.receive<TicketRequest>()

        try {
            return@put call.respond(
                status = HttpStatusCode.OK,
                message = TicketService.update(ticketRequest.toModel(id)).toResponse()
            )
        } catch (e: EntityNotFoundException) {
            return@put call.respond(HttpStatusCode.NotFound)
        }
    }

    delete("/{id}") {
        val id: Long = call.parameters["id"]?.toLong()
            ?: return@delete call.respond(HttpStatusCode.BadRequest)

        try {
            TicketService.deleteById(id)
            return@delete call.respond(HttpStatusCode.OK)
        } catch (e: EntityNotFoundException) {
            return@delete call.respond(HttpStatusCode.NotFound)
        }
    }
}

internal fun Ticket.toResponse(): TicketResponse = TicketResponse(eventId, type.value, price, website, atPlace)

internal fun TicketRequest.toModel(): Ticket = Ticket(eventId, Ticket.TicketType.get(ticketType), ticketPrice, ticketWebsite, atPlace)
internal fun TicketRequest.toModel(eventId: Long): Ticket = Ticket(eventId, Ticket.TicketType.get(ticketType), ticketPrice, ticketWebsite, atPlace)
