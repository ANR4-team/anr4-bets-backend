package db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Participants : Table("participants") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val logoUrl = text("logo_url")
    val sportTypeId = uuid("sport_type_id")
        .references(SportTypes.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id, name = "participants_pk")
}