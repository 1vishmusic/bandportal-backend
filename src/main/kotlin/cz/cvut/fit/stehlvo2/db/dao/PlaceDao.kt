package cz.cvut.fit.stehlvo2.db.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object Places: LongIdTable() {
    val name = varchar("place_name", 64)
    val website = varchar("place_website", 64).nullable()
}

class PlaceDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PlaceDao>(Places)

    var name by Places.name
    var website by Places.website
}