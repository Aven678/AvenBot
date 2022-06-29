package fr.aven.bot.core.database.structures.tickets

import fr.aven.bot.core.database.structures.tickets.Tickets.author
import fr.aven.bot.core.database.structures.tickets.Tickets.id
import fr.aven.bot.core.database.structures.tickets.Tickets.status
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object Tickets : Table("tic_tickets") {
    val id = integer("id")
    val category = (integer("category") references Categories.id)
    val author = varchar("author", 25)
    val status = bool("status")

    override val primaryKey: PrimaryKey = PrimaryKey(arrayOf(category, id))
}

data class Ticket(
    val id: Int,
    val category: Category,
    val author: String,
    val status: Boolean
) {
    companion object {
        fun fromRow(row: ResultRow) = Ticket(row[id], Category.fromRow(row), row[author], row[status])
    }
}