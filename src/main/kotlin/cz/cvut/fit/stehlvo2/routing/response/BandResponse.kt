package cz.cvut.fit.stehlvo2.routing.response

import kotlinx.serialization.Serializable

@Serializable
data class BandResponse(
    val bandId: Long,
    val bandName: String,
    val bandWebsite: String?
)
