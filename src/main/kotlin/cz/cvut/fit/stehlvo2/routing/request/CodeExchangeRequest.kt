package cz.cvut.fit.stehlvo2.routing.request

import kotlinx.serialization.Serializable

@Serializable
data class CodeExchangeRequest(
    val code: String
)
