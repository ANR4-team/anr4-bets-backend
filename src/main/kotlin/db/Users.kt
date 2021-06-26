package db

import org.jetbrains.exposed.sql.Table

object Users : Table("users") {

    val id = varchar("id", 64).uniqueIndex("users_id")
    val login = varchar("login", 64).uniqueIndex("users_login")
    val displayName = varchar("display_name", 64).uniqueIndex("users_display_name")
    val profileImageUrl = text("profile_image_url")

    override val primaryKey = PrimaryKey(id, name = "users_pk")
}