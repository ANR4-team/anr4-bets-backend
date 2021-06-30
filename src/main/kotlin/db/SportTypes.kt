package db

import org.jetbrains.exposed.sql.Table

object SportTypes : Table("sport_types") {

    val id = uuid("id").autoGenerate()
    val name = varchar("name", 128)

    override val primaryKey = PrimaryKey(id, name = "sport_types_pk")
}