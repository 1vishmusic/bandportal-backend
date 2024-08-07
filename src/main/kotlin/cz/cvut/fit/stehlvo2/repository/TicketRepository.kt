package cz.cvut.fit.stehlvo2.repository

import cz.cvut.fit.stehlvo2.db.dao.TicketDao
import cz.cvut.fit.stehlvo2.model.Ticket
import cz.cvut.fit.stehlvo2.repository.util.EntityNotFoundException
import org.jetbrains.exposed.sql.transactions.transaction

object TicketRepository: CrudRepository<Ticket> {
    override fun create(entity: Ticket): Ticket {
        return transaction {
            TicketDao.new(entity.eventId) {
                type = entity.type
                atPlace = entity.atPlace
                price = entity.price
                website = entity.website
            }.toModel()
        }
    }

    override fun readAll(): List<Ticket> {
        return transaction {
            TicketDao.all().map { it.toModel() }
        }
    }

    override fun readById(id: Long): Ticket? {
        return transaction {
            TicketDao.findById(id)?.toModel()
        }
    }

    override fun update(entity: Ticket): Ticket {
        return transaction {
            TicketDao.findByIdAndUpdate(entity.eventId) {
                it.type = entity.type
                it.atPlace = entity.atPlace
                it.price = entity.price
                it.website = entity.website
            }?.toModel()
        } ?: throw EntityNotFoundException("Ticket not found")
    }

    override fun deleteById(id: Long) {
        return transaction {
            val ticket = TicketDao.findById(id) ?: throw EntityNotFoundException("Ticket not found")
            ticket.delete()
        }
    }

    private fun TicketDao.toModel(): Ticket {
        return Ticket(id.value, type, price, website, atPlace)
    }
}