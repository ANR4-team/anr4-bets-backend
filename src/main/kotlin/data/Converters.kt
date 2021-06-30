package data

import data.auth.User
import db.*
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUser(): User {
    return User(
        this[Users.id],
        this[Users.name],
        this[Users.profileImageUrl],
    )
}

fun ResultRow.toSportType(): SportType {
    return SportType(
        this[SportTypes.id],
        this[SportTypes.name],
    )
}