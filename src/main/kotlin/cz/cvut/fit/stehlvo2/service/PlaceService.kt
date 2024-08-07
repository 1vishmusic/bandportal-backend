package cz.cvut.fit.stehlvo2.service

import cz.cvut.fit.stehlvo2.model.Place
import cz.cvut.fit.stehlvo2.repository.PlaceRepository

object PlaceService: CrudService<Place>(PlaceRepository)