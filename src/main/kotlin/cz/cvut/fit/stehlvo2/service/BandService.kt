package cz.cvut.fit.stehlvo2.service

import cz.cvut.fit.stehlvo2.model.Band
import cz.cvut.fit.stehlvo2.repository.BandRepository

object BandService: CrudService<Band>(BandRepository)