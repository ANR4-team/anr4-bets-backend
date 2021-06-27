package repository

import data.*
import data.auth.User
import db.Users
import org.jetbrains.exposed.sql.*

class UserRepository(database: Database) : BaseRepository(database) {

    suspend fun getUser(id: String): User? {
        return dbCall {
            Users.select { Users.id eq id }
                .singleOrNull()
                ?.toUser()
        }
    }

    suspend fun getUsers(): List<User>? {
        return dbCall {
            Users.selectAll().map { it.toUser() }
        }
    }

    suspend fun getUserByName(name: String): User? = dbCall {
        Users.select { Users.name eq name }
            .limit(1)
            .singleOrNull()
            ?.toUser()
    }

    suspend fun createUser(id: String, name: String, profileUrl: String): User? {
        return dbCall {
            Users.insert {
                it[Users.id] = id
                it[Users.name] = name
                it[profileImageUrl] = profileUrl
            }.resultedValues?.singleOrNull()?.toUser()
        }
    }
}
