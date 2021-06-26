package repository

import data.*
import data.auth.User
import db.Users
import org.jetbrains.exposed.sql.*

class UserRepository(database: Database) : BaseRepository(database) {

    suspend fun getUser(id: String): User? {
        return dbCall {
            Users.select { Users.id eq id }
                .singleOrNull()?.toUser()
        }
    }

    suspend fun getUsers(): List<User>? {
        return dbCall {
            Users.selectAll().map { it.toUser() }
        }
    }

    suspend fun getUserIdByLogin(login: String): String? = dbCall {
        Users.slice(Users.id, Users.login)
            .select { Users.login eq login }
            .limit(1)
            .singleOrNull()?.let { it[Users.id] }
    }
}