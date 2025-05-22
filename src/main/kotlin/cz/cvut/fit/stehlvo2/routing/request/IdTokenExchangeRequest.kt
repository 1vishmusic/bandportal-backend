package cz.cvut.fit.stehlvo2.routing.request

import kotlinx.serialization.SerialName

data class IdTokenExchangeRequest(
    @SerialName("id_token") val idToken: String,
)