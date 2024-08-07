package cz.cvut.fit.stehlvo2.routing

import cz.cvut.fit.stehlvo2.model.Event
import cz.cvut.fit.stehlvo2.repository.util.EntityNotFoundException
import cz.cvut.fit.stehlvo2.routing.request.EventRequest
import cz.cvut.fit.stehlvo2.routing.response.EventResponse
import cz.cvut.fit.stehlvo2.service.BandService
import cz.cvut.fit.stehlvo2.service.EventService
import cz.cvut.fit.stehlvo2.service.PlaceService
import cz.cvut.fit.stehlvo2.service.TicketService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDateTime

fun Route.eventRoute() {
    post {
        val eventRequest = call.receive<EventRequest>()
        val createdEvent = EventService.create(eventRequest.toModel())
            ?: return@post call.respond(HttpStatusCode.BadRequest)

        call.respond(
            message = createdEvent.toResponse(),
            status = HttpStatusCode.Created
        )
    }

    get {
        val events = EventService.readAll()

        call.respond(
            message = events.map { it.toResponse() },
            status = HttpStatusCode.OK
        )
    }

    get("/upcoming") {
        val events = EventService.readUpcoming()

        call.respond(
            message = events.map { it.toResponse() },
            status = HttpStatusCode.OK
        )
    }

    get("/{id}") {
        val id: Long = call.parameters["id"]?.toLong() ?:
        return@get call.respond(HttpStatusCode.BadRequest)

        val foundEvent = EventService.readById(id) ?:
        return@get call.respond(HttpStatusCode.NotFound)

        call.respond(
            message = foundEvent.toResponse(),
            status = HttpStatusCode.Created
        )
    }

    put("/{id}") {
        val id: Long = call.parameters["id"]?.toLong() ?:
        return@put call.respond(HttpStatusCode.BadRequest)

        val eventRequest = call.receive<EventRequest>()

        try {
            return@put call.respond(
                status = HttpStatusCode.OK,
                message = EventService.update(eventRequest.toModel(id)).toResponse()
            )
        } catch (e: EntityNotFoundException) {
            return@put call.respond(HttpStatusCode.BadRequest)
        }
    }

    delete("/{id}") {
        val id = call.parameters["id"]?.toLong()
            ?: return@delete call.respond(HttpStatusCode.BadRequest)

        try {
            EventService.deleteById(id)
            return@delete call.respond(HttpStatusCode.OK)
        } catch (e: EntityNotFoundException) {
            return@delete call.respond(HttpStatusCode.BadRequest)
        }
    }
}

internal fun Event.toResponse() = EventResponse(id, name, start.toString(), end?.toString(), website, place.toResponse(), ticket?.toResponse(), bands.map { it.toResponse() })

internal fun EventRequest.toModel() = Event(
    name = eventName,
    start = LocalDateTime.parse(eventStart),
    end = if(eventEnd == null) null else LocalDateTime.parse(eventEnd),
    website = eventWebsite,
    place = PlaceService.readById(placeId)
        ?: throw EntityNotFoundException("Place with placeId=$placeId was not found"),
    ticket = null,
    bands = bands.map {
        BandService.readById(it) ?: throw EntityNotFoundException("Band with bandId=$it was not found")
    }.toList()
)
internal fun EventRequest.toModel(id: Long) = Event(
    id,
    eventName,
    LocalDateTime.parse(eventStart),
    if(eventEnd == null) null else LocalDateTime.parse(eventEnd),
    eventWebsite,
    PlaceService.readById(placeId)
        ?: throw EntityNotFoundException("Place with placeId=$placeId was not found"),
    TicketService.readById(id),
    bands.map {
        BandService.readById(it) ?: throw EntityNotFoundException("Band with bandId=$it was not found")
    }.toList()
)
