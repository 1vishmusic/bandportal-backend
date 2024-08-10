package cz.cvut.fit.stehlvo2.db.dao

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object EventBands: Table() {
    val eventId = reference("event_id", Events, onDelete = ReferenceOption.CASCADE)
    val bandId = reference("band_id", Bands, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(eventId, bandId, name = "PK_EventBand")
}