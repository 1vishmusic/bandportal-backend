package cz.cvut.fit.stehlvo2.service

import cz.cvut.fit.stehlvo2.model.Ticket
import cz.cvut.fit.stehlvo2.repository.TicketRepository

object TicketService: CrudService<Ticket>(TicketRepository)