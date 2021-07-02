package repository

import data.SportType
import db.SportTypes
import org.jetbrains.exposed.sql.*
import java.util.*

class SportTypeRepository(database: Database) : ExposedRepository<SportTypes, SportType>(database) {

    override val table = SportTypes

    override fun ResultRow.convert(): SportType {
        return SportType(
            this[SportTypes.id],
            this[SportTypes.name],
        )
    }

    suspend fun getTypeById(id: UUID): SportType? {
        return dbCall {
            SportTypes.select { SportTypes.id eq id }
                .querySingle()
        }
    }

    suspend fun getTypeByName(name: String): SportType? {
        return dbCall {
            SportTypes.select { SportTypes.name eq name }
                .querySingle()
        }
    }
    
    suspend fun insertType(name: String): SportType? {
        return dbCall {
            SportTypes.insert {
                it[SportTypes.name] = name
            }.inserted()
        }
    }

    suspend fun updateType(id: UUID, newName: String): SportType? {
        return dbCall {
            SportTypes.update({ SportTypes.id eq id }) {
                it[name] = newName
            }
            SportTypes.select { SportTypes.id eq id }.querySingle()
        }
    }

    suspend fun deleteType(id: UUID): Boolean {
        return dbCall {
            SportTypes.deleteWhere { SportTypes.id eq id }
        }?.let { it > 0 } == true
    }
}