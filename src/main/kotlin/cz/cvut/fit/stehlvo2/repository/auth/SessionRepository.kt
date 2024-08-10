package cz.cvut.fit.stehlvo2.repository.auth

import cz.cvut.fit.stehlvo2.service.auth.Session

object SessionRepository {
    private val sessions: MutableMap<String, Session> = mutableMapOf()

    fun create(entity: Session): Session {
        sessions[entity.sessionToken] = entity
        return entity
    }

    fun readByToken(sessionToken: String): Session? {
        return sessions[sessionToken]
    }

    fun deleteByToken(sessionToken: String) {
        sessions.remove(sessionToken)
    }
}