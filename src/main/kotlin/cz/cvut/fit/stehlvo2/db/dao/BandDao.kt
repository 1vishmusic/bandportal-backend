package cz.cvut.fit.stehlvo2.db.dao

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object Bands: LongIdTable() {
    val name = varchar("band_name", 255)
    val website = varchar("band_website", 255).nullable()
}

class BandDao(id: EntityID<Long>): LongEntity(id) {
    companion object: LongEntityClass<BandDao>(Bands)

    var name by Bands.name
    var website by Bands.website
}
