package cz.cvut.fit.stehlvo2.service

import cz.cvut.fit.stehlvo2.repository.CrudRepository

abstract class CrudService<T, > (
    protected val repository: CrudRepository<T>
) {
    open fun create(model: T): T? = repository.create(model)

    open fun readAll(): List<T> = repository.readAll()

    open fun readById(id: Long): T? = repository.readById(id)

    open fun update(model: T): T = repository.update(model)

    open fun deleteById(id: Long) = repository.deleteById(id)
}