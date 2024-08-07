package cz.cvut.fit.stehlvo2.service

import cz.cvut.fit.stehlvo2.repository.CrudRepository

abstract class CrudService<T, > (
    protected val repository: CrudRepository<T>
) {
    fun create(model: T): T? = repository.create(model)

    fun readAll(): List<T> = repository.readAll()

    fun readById(id: Long): T? = repository.readById(id)

    fun update(model: T): T = repository.update(model)

    fun deleteById(id: Long) = repository.deleteById(id)
}