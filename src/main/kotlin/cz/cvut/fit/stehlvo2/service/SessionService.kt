package cz.cvut.fit.stehlvo2.service

import cz.cvut.fit.stehlvo2.config.Config
import io.ktor.client.engine.cio.*
import cz.cvut.fit.stehlvo2.repository.auth.SessionRepository
import cz.cvut.fit.stehlvo2.service.auth.GoogleUserResponse
import cz.cvut.fit.stehlvo2.service.auth.Session
import cz.cvut.fit.stehlvo2.service.auth.TokenRequest
import cz.cvut.fit.stehlvo2.service.auth.TokenResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import java.util.*

object SessionService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    fun isSessionTokenValid(token: String): Boolean {
        val session = SessionRepository.readByToken(token)
        if(session == null)
            return false

        // Check for expiry
        if(((System.currentTimeMillis() / 1000).toInt() - session.issueTime) > session.expiresIn) {
            SessionRepository.deleteByToken(token)
            return false
        }

        return true
    }

    fun getSession(token: String): Session? {
        return SessionRepository.readByToken(token)
    }

    suspend fun handleCodeExchange(code: String): Session? {
        val tokenResponse = client.post(Config.getOAuthTokenUri()) {
            contentType(ContentType.Application.Json)
            setBody(
                TokenRequest(
                    client_id = Config.getOAuthClientId(),
                    client_secret = Config.getOAuthClientSecret(),
                    code = code,
                    grant_type = "authorization_code",
                    redirect_uri = Config.getOAuthRedirectUri(),
                )
            )
        }
        if (!tokenResponse.status.isSuccess()) {
            println("Authentication failed, Status Code: ${tokenResponse.status.value}, Message: ${tokenResponse.body<String>()}")
            return null
        }
        val tokenResponseObj: TokenResponse = tokenResponse.body()

        val userResponse = client.post("https://www.googleapis.com/oauth2/v3/userinfo") {
            contentType(ContentType.Application.Json)
            headers {
                append("Authorization", "Bearer ${tokenResponseObj.access_token}")
            }
        }
        if (!userResponse.status.isSuccess()) {
            println("Reading user information failed, Status: ${userResponse.status.value}, Message: ${userResponse.body<String>()}")
            return null
        }
        val userResponseObj: GoogleUserResponse = userResponse.body()

        println("Received user data from Google API: ${userResponse.body<String>()}")

        if (userResponseObj.email != "band@1vishmusic.com") {
            println("Attempted to login with unknown email: " + userResponseObj.email)
            return null
        }

        if (!userResponseObj.email_verified) {
            println("Unable to log user in: Email ain't verified")
            return null
        }

        println("Creating session for " + userResponseObj.email)

        return SessionRepository.create(
            Session(
                username = userResponseObj.name,
                email = userResponseObj.email,
                sessionToken = UUID.randomUUID().toString(),
                bearerToken = tokenResponseObj.access_token,
                issueTime = (System.currentTimeMillis() / 1000).toInt(),
                expiresIn = tokenResponseObj.expires_in
            )
        )
    }
}