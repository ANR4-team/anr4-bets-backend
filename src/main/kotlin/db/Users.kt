package db

import org.jetbrains.exposed.sql.Table

object Users : Table("users") {

    val id = varchar("id", 128).uniqueIndex("users_id")
    val name = varchar("name", 128).uniqueIndex("users_name")
    val profileImageUrl = text("profile_image_url")

    override val primaryKey = PrimaryKey(id, name = "users_pk")
}