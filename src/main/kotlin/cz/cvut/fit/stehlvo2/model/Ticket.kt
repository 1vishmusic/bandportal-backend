package cz.cvut.fit.stehlvo2.model

data class Ticket(
    val eventId: Long,
    val type: TicketType,

    val price: String?,
    val website: String?,
    val atPlace: Boolean
) {
    enum class TicketType(val value: Int) {
        NORMAL(0),
        FREE(1),
        VOLUNTARY(2),
        UNKNOWN(3);

        companion object {
            fun get(type: Int): TicketType {
                for (t in entries) {
                    if (t.value == type) {
                        return t
                    }
                }

                return UNKNOWN
            }
        }
    }
}