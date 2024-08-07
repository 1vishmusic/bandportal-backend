package cz.cvut.fit.stehlvo2.repository

interface CrudRepository<T> {
    fun create(entity: T): T

    fun readAll(): List<T>

    fun readById(id: Long): T?

    fun update(entity: T): T

    fun deleteById(id: Long)
}