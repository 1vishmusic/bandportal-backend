package cz.cvut.fit.stehlvo2.routing

import cz.cvut.fit.stehlvo2.model.Event
import cz.cvut.fit.stehlvo2.routing.response.WebEventResponse
import cz.cvut.fit.stehlvo2.service.EventService
import cz.cvut.fit.stehlvo2.service.GalleryService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.format.DateTimeFormatter

fun Route.webRoute() {
    get("/event") {
        val events = EventService.readUpcoming()

        return@get call.respond(
            message = events.map { it.toWebResponse() },
            status = HttpStatusCode.OK
        )
    }
    get("/gallery") {
        val albums = GalleryService.readAlbums()

        return@get call.respond(
            message = albums,
            status = HttpStatusCode.OK,
        )
    }
}

internal fun Event.toWebResponse(): WebEventResponse = WebEventResponse(
    name = name,
    date = start.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString(),
    place = if (name == "Soukromá akce") "Soukromá akce" else place.name,
    webpage = website,
    placeWebpage = place.website
)