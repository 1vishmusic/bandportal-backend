package cz.cvut.fit.stehlvo2.repository

import cz.cvut.fit.stehlvo2.db.dao.BandDao
import cz.cvut.fit.stehlvo2.model.Band
import cz.cvut.fit.stehlvo2.repository.util.DataAccessException
import cz.cvut.fit.stehlvo2.repository.util.EntityNotFoundException
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.transactions.transaction

object BandRepository: CrudRepository<Band> {
    override fun create(entity: Band): Band {
        return transaction {
            BandDao.new {
                name = entity.name
                website = entity.website
            }.toModel()
        }
    }

    override fun readAll(): List<Band> {
        return transaction {
            BandDao.all().map { it.toModel() }.toList()
        }
    }

    override fun readById(id: Long): Band? {
        return transaction {
            BandDao.findById(id)?.toModel()
        }
    }

    override fun update(entity: Band): Band {
        return transaction {
            BandDao.findByIdAndUpdate(entity.id) {
                it.name = entity.name
                it.website = entity.website
            }?.toModel()
        } ?: throw EntityNotFoundException("Band with id ${entity.id} was not found")
    }

    override fun deleteById(id: Long) {
        transaction {
            try {
                val band = BandDao.findById(id) ?: throw EntityNotFoundException("Band with id $id was not found")
                band.delete()
            } catch (e: ExposedSQLException) {
                throw DataAccessException(e)
            }
        }
    }

    private fun BandDao.toModel(): Band {
        return Band(id.value, name, website)
    }
}