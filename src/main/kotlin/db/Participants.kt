package db

import org.jetbrains.exposed.sql.Table

object Participants : Table("participants") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val logoUrl = text("logo_url")

    override val primaryKey = PrimaryKey(id, name = "participants_pk")
}