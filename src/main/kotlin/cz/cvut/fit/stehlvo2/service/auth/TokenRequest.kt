package cz.cvut.fit.stehlvo2.service.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest(
    val client_id: String,
    val client_secret: String,
    val code: String,
    val grant_type: String,
    val redirect_uri: String,
)
