package cz.cvut.fit.stehlvo2.routing.response

import kotlinx.serialization.Serializable

@Serializable
data class TicketResponse(
    val eventId: Long,
    val ticketType: Int,

    val ticketPrice: String?,
    val ticketWebsite: String?,
    val atPlace: Boolean
)