package cz.cvut.fit.stehlvo2.service

import cz.cvut.fit.stehlvo2.model.Event
import cz.cvut.fit.stehlvo2.repository.EventRepository

object EventService: CrudService<Event>(EventRepository) {
    fun readUpcoming(): List<Event> {
        return EventRepository.readUpcoming()
    }
}