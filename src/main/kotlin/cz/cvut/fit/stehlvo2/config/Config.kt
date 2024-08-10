package cz.cvut.fit.stehlvo2.config

import io.github.cdimascio.dotenv.dotenv

object Config {
    val dotenv = dotenv {
        ignoreIfMissing = true
    }

    fun getDbDriver(): String {
        return dotenv["DB_DRIVER"]
    }

    fun getDbUrl(): String {
        return dotenv["DB_URL"]
    }

    fun getDbUsername(): String {
        return dotenv["DB_USERNAME"]
    }

    fun getDbPassword(): String {
        return dotenv["DB_PASSWORD"]
    }

    fun getOAuthClientId(): String {
        return dotenv["OAUTH_CLIENT_ID"]
    }

    fun getOAuthClientSecret(): String {
        return dotenv["OAUTH_CLIENT_SECRET"]
    }

    fun getOAuthRedirectUri(): String {
        return dotenv["OAUTH_REDIRECT_URI"]
    }

    fun getOAuthTokenUri(): String {
        return dotenv["OAUTH_TOKEN_URI"]
    }
}