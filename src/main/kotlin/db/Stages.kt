package db

import data.StageType
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Stages : Table("stages") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val type = enumerationByName("type", 32, StageType::class)
    val tournamentId = integer("tournament_id")
        .references(Tournaments.id, onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id, name = "stages_pk")
}
