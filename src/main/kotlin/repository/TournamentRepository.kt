package repository

import data.SportType
import data.Tournament
import data.TournamentCreateBody
import data.TournamentUpdateBody
import data.auth.User
import db.SportTypes
import db.Tournaments
import db.Users
import org.jetbrains.exposed.sql.*

class TournamentRepository(database: Database) : ExposedRepository<Tournaments, Tournament>(database) {

    override val table = Tournaments

    override fun ResultRow.convert(): Tournament {
        return Tournament(
            this[Tournaments.id],
            this[Tournaments.name],
            this[Tournaments.startDate],
            this[Tournaments.endDate],
            this[Tournaments.logoUrl],
            SportType(
                this[SportTypes.id],
                this[SportTypes.name],
            ),
            User(
                this[Users.id],
                this[Users.name],
                this[Users.profileImageUrl],
            )
        )
    }

    override suspend fun getAll(): List<Tournament>? {
        return dbCall {
            Tournaments.leftJoin(SportTypes).leftJoin(Users)
                .selectAll()
                .map { it.convert() }
        }
    }

    suspend fun getById(id: Int): Tournament? {
        return dbCall {
            Tournaments.leftJoin(SportTypes).leftJoin(Users)
                .select { Tournaments.id eq id }
                .querySingle()
        }
    }

    suspend fun insert(createBody: TournamentCreateBody, creatorId: String): Tournament? {
        return dbCall {
            val newId = Tournaments.insert {
                it[name] = createBody.name
                it[startDate] = createBody.startDate
                it[endDate] = createBody.endDate
                it[logoUrl] = createBody.logoUrl
                it[sportTypeId] = createBody.sportTypeId
                it[Tournaments.creatorId] = creatorId
            }.resultedValues?.singleOrNull()?.get(Tournaments.id) ?: return@dbCall null
            Tournaments.leftJoin(SportTypes).leftJoin(Users)
                .select { Tournaments.id eq newId }
                .querySingle()
        }
    }

    suspend fun update(id: Int, updateBody: TournamentUpdateBody): Tournament? {
        return dbCall {
            Tournaments.update({ Tournaments.id eq id }) {
                updateBody.name?.let { newValue -> it[name] = newValue }
                updateBody.logoUrl?.let { newValue -> it[logoUrl] = newValue }
                updateBody.startDate?.let { newValue -> it[startDate] = newValue }
                updateBody.endDate?.let { newValue -> it[endDate] = newValue }
                updateBody.sportTypeId?.let { newValue -> it[sportTypeId] = newValue }
            }
            Tournaments.leftJoin(SportTypes).leftJoin(Users)
                .select { Tournaments.id eq id }
                .querySingle()
        }
    }

    suspend fun delete(id: Int): Boolean {
        return dbCall {
            Tournaments.deleteWhere { Tournaments.id eq id }
        }?.let { it > 0 } == true
    }
}