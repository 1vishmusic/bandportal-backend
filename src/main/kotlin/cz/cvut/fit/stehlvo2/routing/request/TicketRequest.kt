package cz.cvut.fit.stehlvo2.routing.request

import kotlinx.serialization.Serializable

@Serializable
data class TicketRequest(
    val eventId: Long,
    val ticketType: Int,
    val ticketPrice: String?,
    val ticketWebsite: String?,
    val atPlace: Boolean
)
