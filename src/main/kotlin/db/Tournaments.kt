package db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object Tournaments : Table("tournaments") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val startDate = datetime("start_date")
    val endDate = datetime("end_date")
    val logoUrl = text("logo_url")
    val sportTypeId = uuid("sport_type_id")
        .references(SportTypes.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val creatorId = varchar("creator_id", 128)
        .references(Users.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id, name = "tournaments_pk")
}