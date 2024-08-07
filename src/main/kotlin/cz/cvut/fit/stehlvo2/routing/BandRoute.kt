package cz.cvut.fit.stehlvo2.routing

import cz.cvut.fit.stehlvo2.model.Band
import cz.cvut.fit.stehlvo2.repository.util.DataAccessException
import cz.cvut.fit.stehlvo2.repository.util.EntityNotFoundException
import cz.cvut.fit.stehlvo2.routing.request.BandRequest
import cz.cvut.fit.stehlvo2.routing.response.BandResponse
import cz.cvut.fit.stehlvo2.service.BandService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.bandRoute() {
    post {
        val bandRequest = call.receive<BandRequest>()
        val createdBand = BandService.create(bandRequest.toModel())
            ?: return@post call.respond(HttpStatusCode.BadRequest)

        call.respond(
            message = createdBand.toResponse(),
            status = HttpStatusCode.Created
        )
    }

    get {
        val bands = BandService.readAll()

        call.respond(
            message = bands.map { it.toResponse() },
            status = HttpStatusCode.OK
        )
    }

    get("/{id}") {
        val id: Long = call.parameters["id"]?.toLong() ?:
            return@get call.respond(HttpStatusCode.BadRequest)
        val foundBand = BandService.readById(id) ?:
            return@get call.respond(HttpStatusCode.NotFound)

        call.respond(
            message = foundBand.toResponse(),
            status = HttpStatusCode.Created
        )
    }

    put("/{id}") {
        val id: Long = call.parameters["id"]?.toLong() ?:
            return@put call.respond(HttpStatusCode.BadRequest)

        val bandRequest = call.receive<BandRequest>()

        try {
            return@put call.respond(
                status = HttpStatusCode.OK,
                message = BandService.update(bandRequest.toModel(id)).toResponse()
            )
        } catch (e: EntityNotFoundException) {
            return@put call.respond(HttpStatusCode.NotFound)
        }
    }

    delete("/{id}") {
        val id = call.parameters["id"]?.toLong()
            ?: return@delete call.respond(HttpStatusCode.BadRequest)

        try {
            BandService.deleteById(id)
            return@delete call.respond(HttpStatusCode.OK)
        } catch (e: EntityNotFoundException) {
            return@delete call.respond(HttpStatusCode.NotFound)
        } catch (e: DataAccessException) {
            return@delete call.respond(HttpStatusCode.Conflict, "Could not delete entity due to Event is dependent on this Band.")
        }
    }
}

internal fun Band.toResponse() = BandResponse(id, name, website)

private fun BandRequest.toModel() = Band(name = bandName, website = bandWebsite)
private fun BandRequest.toModel(id: Long) = Band(id, bandName, bandWebsite)
