package cz.cvut.fit.stehlvo2.model

import java.time.LocalDateTime

data class Event(
    val id: Long = 0,
    val name: String,
    val start: LocalDateTime,
    val end: LocalDateTime?,
    val website: String?,

    val place: Place,
    val ticket: Ticket?,
    val bands: List<Band>
)
