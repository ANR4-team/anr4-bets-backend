package repository

import data.auth.User
import db.Users
import org.jetbrains.exposed.sql.*

class UserRepository(database: Database) : ExposedRepository<Users, User>(database) {

    override val table = Users

    override fun ResultRow.convert(): User {
        return User(
            this[Users.id],
            this[Users.name],
            this[Users.profileImageUrl],
        )
    }

    suspend fun getUser(id: String): User? {
        return dbCall {
            Users.select { Users.id eq id }.querySingle()
        }
    }

    suspend fun getUserByName(name: String): User? = dbCall {
        Users.select { Users.name eq name }.querySingle()
    }

    suspend fun createUser(id: String, name: String, profileUrl: String): User? {
        return dbCall {
            Users.insert {
                it[Users.id] = id
                it[Users.name] = name
                it[profileImageUrl] = profileUrl
            }.inserted()
        }
    }

    suspend fun updateUser(id: String, name: String, profileUrl: String): User? {
        return dbCall {
            Users.update({ Users.id eq id }) {
                it[Users.name] = name
                it[profileImageUrl] = profileUrl
            }
            Users.select { Users.id eq id }.querySingle()
        }
    }
}
