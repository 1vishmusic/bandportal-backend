package cz.cvut.fit.stehlvo2.routing

import cz.cvut.fit.stehlvo2.model.Place
import cz.cvut.fit.stehlvo2.repository.util.DataAccessException
import cz.cvut.fit.stehlvo2.repository.util.EntityNotFoundException
import cz.cvut.fit.stehlvo2.routing.request.PlaceRequest
import cz.cvut.fit.stehlvo2.routing.response.PlaceResponse
import cz.cvut.fit.stehlvo2.service.PlaceService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.placeRoute() {
    post {
        val placeRequest = call.receive<PlaceRequest>()
        val createdPlace = PlaceService.create(placeRequest.toModel())
            ?: return@post call.respond(HttpStatusCode.BadRequest)

        call.respond(
            message = createdPlace.toResponse(),
            status = HttpStatusCode.Created
        )
    }

    get {
        val places = PlaceService.readAll()

        call.respond(
            message = places.map(Place::toResponse),
            status = HttpStatusCode.OK
        )
    }

    get("/{id}") {
        val id: Long = call.parameters["id"]?.toLong() ?:
            return@get call.respond(HttpStatusCode.BadRequest)
        val foundPlace = PlaceService.readById(id) ?:
            return@get call.respond(HttpStatusCode.NotFound)

        call.respond(
            message = foundPlace.toResponse(),
            status = HttpStatusCode.Created
        )
    }

    put("/{id}") {
        val id: Long = call.parameters["id"]?.toLong()
            ?: return@put call.respond(HttpStatusCode.BadRequest)

        val placeRequest = call.receive<PlaceRequest>()
        try {
            return@put call.respond(
                status = HttpStatusCode.OK,
                message = PlaceService.update(placeRequest.toModel(id)).toResponse()
            )
        } catch (e: EntityNotFoundException) {
            return@put call.respond(HttpStatusCode.BadRequest)
        }
    }

    delete("/{id}") {
        val id: Long = call.parameters["id"]?.toLong()
            ?: return@delete call.respond(HttpStatusCode.BadRequest)

        try {
            PlaceService.deleteById(id)
            return@delete call.respond(HttpStatusCode.OK)
        } catch (e: EntityNotFoundException) {
            return@delete call.respond(HttpStatusCode.NotFound)
        } catch (e: DataAccessException) {
            return@delete call.respond(HttpStatusCode.Conflict, "Could not delete entity due to Event is dependent on this Place.")
        }
    }
}

internal fun Place.toResponse(): PlaceResponse = PlaceResponse(id, name, website)

private fun PlaceRequest.toModel(): Place = Place(0, placeName, placeWebsite)
private fun PlaceRequest.toModel(id: Long): Place = Place(id, placeName, placeWebsite)