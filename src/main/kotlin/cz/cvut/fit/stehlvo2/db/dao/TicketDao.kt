package cz.cvut.fit.stehlvo2.db.dao

import cz.cvut.fit.stehlvo2.model.Ticket
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Tickets: IdTable<Long>() {
    override val id = reference("event_id", Events, ReferenceOption.CASCADE)

    val ticketType = enumeration("ticket_type", Ticket.TicketType::class)
    val price = varchar("ticket_price", 64).nullable()
    val website = varchar("ticket_website", 256).nullable()
    val atPlace = bool("at_place")
}

class TicketDao(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<TicketDao>(Tickets)

    var type by Tickets.ticketType
    var price by Tickets.price
    var website by Tickets.website
    var atPlace by Tickets.atPlace
}