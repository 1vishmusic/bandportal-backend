package cz.cvut.fit.stehlvo2.repository

import cz.cvut.fit.stehlvo2.db.dao.PlaceDao
import cz.cvut.fit.stehlvo2.model.Place
import cz.cvut.fit.stehlvo2.repository.util.DataAccessException
import cz.cvut.fit.stehlvo2.repository.util.EntityNotFoundException
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.transactions.transaction

object PlaceRepository: CrudRepository<Place> {
    override fun create(entity: Place): Place {
        return transaction {
            PlaceDao.new {
                name = entity.name
                website = entity.website
            }.toModel()
        }
    }

    override fun readAll(): List<Place> {
        return transaction {
            PlaceDao.all().map { it.toModel() }.toList()
        }
    }

    override fun readById(id: Long): Place? {
        return transaction {
            PlaceDao.findById(id)?.toModel()
        }
    }

    override fun deleteById(id: Long) {
        transaction {
            try {
                PlaceDao.findById(id)?.delete() ?: throw EntityNotFoundException("Place with id $id was not found")
            } catch (e: ExposedSQLException) {
                throw DataAccessException(e)
            }
        }
    }

    override fun update(entity: Place): Place {
        return transaction {
            PlaceDao.findByIdAndUpdate(entity.id) {
                it.name = entity.name
                it.website = entity.website
            }?.toModel()
        } ?: throw EntityNotFoundException("Place with id ${entity.id} was not found")
    }

    private fun PlaceDao.toModel(): Place {
        return Place(id.value, name, website)
    }
}