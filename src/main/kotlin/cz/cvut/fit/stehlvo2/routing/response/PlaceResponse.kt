package cz.cvut.fit.stehlvo2.routing.response

import kotlinx.serialization.Serializable

@Serializable
data class PlaceResponse (
    val placeId: Long,
    val placeName: String,
    val placeWebsite: String?
)