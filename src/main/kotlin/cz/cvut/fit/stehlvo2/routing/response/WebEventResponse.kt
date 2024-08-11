package cz.cvut.fit.stehlvo2.routing.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebEventResponse(
    val name: String,
    val date: String,
    val place: String,

    val webpage: String?,
    @SerialName("place_webpage") val placeWebpage: String?,
)