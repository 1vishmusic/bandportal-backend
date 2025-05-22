package cz.cvut.fit.stehlvo2.routing.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IdTokenExchangeResponse(
    @SerialName("session_token") val sessionToken: String,
)