package cz.cvut.fit.stehlvo2.routing.response

import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    val eventId: Long,
    val eventName: String,
    val eventStart: String,
    val eventEnd: String?,
    val eventWebsite: String?,

    val place: PlaceResponse,
    val ticket: TicketResponse?,

    val bands: List<BandResponse>,
)
