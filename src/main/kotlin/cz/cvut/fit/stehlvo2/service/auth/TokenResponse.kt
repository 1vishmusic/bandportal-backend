package cz.cvut.fit.stehlvo2.service.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val access_token: String,
    val expires_in: Int, // In seconds
    val refresh_token: String? = null, // Looks like XORed with id_token
    val scope: String,
    val token_type: String,
    val id_token: String? = null, // Wtf
)
