package cz.cvut.fit.stehlvo2.routing.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val username: String,
    val email: String
)
