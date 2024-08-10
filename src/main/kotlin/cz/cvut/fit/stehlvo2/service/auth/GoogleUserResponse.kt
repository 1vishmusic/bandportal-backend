package cz.cvut.fit.stehlvo2.service.auth

import kotlinx.serialization.Serializable

@Serializable
data class GoogleUserResponse(
    val sub: String,
    val name: String,
    val given_name: String,
    val family_name: String,
    val picture: String,
    val email: String,
    val email_verified: Boolean,
)
