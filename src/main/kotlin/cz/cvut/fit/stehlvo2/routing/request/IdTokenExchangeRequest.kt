package cz.cvut.fit.stehlvo2.routing.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IdTokenExchangeRequest(
    @SerialName("id_token") val idToken: String,
)