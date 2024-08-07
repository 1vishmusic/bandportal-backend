package cz.cvut.fit.stehlvo2.repository

import cz.cvut.fit.stehlvo2.db.dao.BandDao
import cz.cvut.fit.stehlvo2.db.dao.EventDao
import cz.cvut.fit.stehlvo2.db.dao.PlaceDao
import cz.cvut.fit.stehlvo2.db.dao.TicketDao
import cz.cvut.fit.stehlvo2.model.Band
import cz.cvut.fit.stehlvo2.model.Event
import cz.cvut.fit.stehlvo2.model.Place
import cz.cvut.fit.stehlvo2.model.Ticket
import cz.cvut.fit.stehlvo2.repository.util.EntityNotFoundException
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object EventRepository: CrudRepository<Event> {
    override fun create(entity: Event): Event {
        return transaction {
            EventDao.new {
                name = entity.name
                start = entity.start
                end = entity.end
                website = entity.website

                place = PlaceDao[entity.place.id]
                bands = SizedCollection(entity.bands.map { BandDao[it.id] })
            }.toModel()
        }
    }

    override fun readAll(): List<Event> {
        return transaction {
            EventDao.all().sortedBy { it.start }.map { it.toModel() }
        }
    }

    fun readUpcoming(): List<Event> {
        return transaction {
            EventDao.all()
                .filter { it.start.isAfter(LocalDateTime.now()) }
                .map { it.toModel() }
                .sortedBy { it.start }
        }
    }

    override fun readById(id: Long): Event? {
        return transaction {
            EventDao.findById(id)?.toModel()
        }
    }

    override fun update(entity: Event): Event {
        return transaction {
            EventDao.findByIdAndUpdate(entity.id) {
                it.name = entity.name
                it.start = entity.start
                it.end = entity.end
                it.website = entity.website

                it.place = PlaceDao[entity.place.id]
                it.bands = SizedCollection(entity.bands.map { b -> BandDao[b.id] })
            }?.toModel()
        } ?: throw EntityNotFoundException("Event with id ${entity.id} not found")
    }

    override fun deleteById(id: Long) {
        transaction {
            EventDao.findById(id)?.delete()
                ?: throw EntityNotFoundException("Event with id $id was not found")
        }
    }

    private fun EventDao.toModel(): Event {
        return Event(id.value, name, start, end, website, place.toModel(), TicketDao.findById(id.value)?.toModel(), bands.map { it.toModel() })
    }
    private fun PlaceDao.toModel(): Place {
        return Place(id.value, name, website)
    }
    private fun BandDao.toModel(): Band {
        return Band(id.value, name, website)
    }
    private fun TicketDao.toModel(): Ticket {
        return Ticket(id.value, type, price, website, atPlace)
    }
}
