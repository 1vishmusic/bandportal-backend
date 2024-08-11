package cz.cvut.fit.stehlvo2.service

import cz.cvut.fit.stehlvo2.model.Event
import cz.cvut.fit.stehlvo2.repository.EventRepository

object EventService: CrudService<Event>(EventRepository) {
    private var eventCache: List<Event> = mutableListOf()
    private var upcomingEventCache: List<Event> = mutableListOf()

    private var isEventCacheLoaded: Boolean = false
    private var isUpcomingEventCacheLoaded: Boolean = false

    fun readUpcoming(): List<Event> {
        if(isUpcomingEventCacheLoaded) {
            return upcomingEventCache
        }

        upcomingEventCache = EventRepository.readUpcoming()
        isUpcomingEventCacheLoaded = true

        return upcomingEventCache
    }

    override fun readAll(): List<Event> {
        if(isEventCacheLoaded) {
            return eventCache
        }

        eventCache = super.readAll()
        isEventCacheLoaded = true

        return eventCache
    }

    override fun create(model: Event): Event? {
        invalidateEventCache()
        return super.create(model)
    }

    override fun update(model: Event): Event {
        invalidateEventCache()
        return super.update(model)
    }

    override fun deleteById(id: Long) {
        invalidateEventCache()
        super.deleteById(id)
    }

    fun invalidateEventCache() {
        isEventCacheLoaded = false
        isUpcomingEventCacheLoaded = false
    }
}