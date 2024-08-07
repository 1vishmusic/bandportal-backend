package cz.cvut.fit.stehlvo2.routing.request

import kotlinx.serialization.Serializable

@Serializable
data class EventRequest(
    val eventName: String,
    val eventStart: String,
    val eventEnd: String?,
    val eventWebsite: String?,

    val placeId: Long,
    val bands: List<Long>
)
