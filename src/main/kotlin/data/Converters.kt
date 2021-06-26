package data

import data.auth.User
import db.*
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUser(): User {
    return User(
        this[Users.id],
        this[Users.login],
        this[Users.displayName],
        this[Users.profileImageUrl],
    )
}