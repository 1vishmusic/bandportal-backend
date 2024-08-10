package cz.cvut.fit.stehlvo2.service.auth

data class Session(
    val username: String,
    val sessionToken: String,
    val bearerToken: String,
    val email: String,
    val issueTime: Int,
    val expiresIn: Int // Seconds
)
