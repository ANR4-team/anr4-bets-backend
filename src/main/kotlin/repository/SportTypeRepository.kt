package repository

import data.SportType
import data.toSportType
import db.SportTypes
import org.jetbrains.exposed.sql.*
import java.util.*

class SportTypeRepository(database: Database) : BaseRepository(database) {

    suspend fun getAllTypes(): List<SportType> {
        return dbCall {
            SportTypes.selectAll().map { it.toSportType() }
        } ?: emptyList()
    }

    suspend fun getTypeById(id: UUID): SportType? {
        return dbCall {
            SportTypes.select { SportTypes.id eq id }
                .limit(1)
                .singleOrNull()
                ?.toSportType()
        }
    }

    suspend fun getTypeByName(name: String): SportType? {
        return dbCall {
            SportTypes.select { SportTypes.name eq name }
                .limit(1)
                .singleOrNull()
                ?.toSportType()
        }
    }
    
    suspend fun insertType(name: String): SportType? {
        return dbCall {
            SportTypes.insert {
                it[SportTypes.name] = name
            }.resultedValues?.singleOrNull()?.toSportType()
        }
    }

    suspend fun updateType(id: UUID, newName: String): SportType? {
        return dbCall {
            SportTypes.update({ SportTypes.id eq id }) {
                it[name] = newName
            }
            SportTypes.select { SportTypes.id eq id }
                .limit(1)
                .singleOrNull()
                ?.toSportType()
        }
    }

    suspend fun deleteType(id: UUID): Boolean {
        return dbCall {
            SportTypes.deleteWhere { SportTypes.id eq id }
        }?.let { it > 0 } == true
    }
}