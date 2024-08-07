package cz.cvut.fit.stehlvo2.db.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime

object Events: LongIdTable() {
    val name = varchar("event_name", 48)
    val start = datetime("start_time")
    val end = datetime("end_time").nullable()
    val website = varchar("event_website", 64).nullable()

    val place = reference("place_id", Places, onDelete = ReferenceOption.RESTRICT)
}

class EventDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<EventDao>(Events)

    var name by Events.name
    var start by Events.start
    var end by Events.end
    var website by Events.website

    var place by PlaceDao referencedOn Events.place
    var bands by BandDao via EventBands
}
