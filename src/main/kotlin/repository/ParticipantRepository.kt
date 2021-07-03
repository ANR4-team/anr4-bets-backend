package repository

import data.Participant
import data.SportType
import db.Participants
import db.SportTypes
import org.jetbrains.exposed.sql.*
import java.util.*

class ParticipantRepository(database: Database) : ExposedRepository<Participants, Participant>(database) {

    override val table = Participants

    override fun ResultRow.convert(): Participant {
        return Participant(
            this[Participants.id],
            this[Participants.name],
            this[Participants.logoUrl],
            SportType(
                this[SportTypes.id],
                this[SportTypes.name],
            )
        )
    }

    override suspend fun getAll(): List<Participant>? {
        return dbCall {
            Participants.leftJoin(SportTypes)
                .selectAll()
                .map { it.convert() }
        }
    }

    suspend fun getParticipantById(id: Int): Participant? {
        return dbCall {
            Participants.leftJoin(SportTypes)
                .select { Participants.id eq id }
                .querySingle()
        }
    }

    suspend fun insertParticipant(name: String, logoUrl: String, sportTypeId: UUID): Participant? {
        return dbCall {
            val id = Participants.insert {
                it[Participants.name] = name
                it[Participants.logoUrl] = logoUrl
                it[Participants.sportTypeId] = sportTypeId
            }.resultedValues?.singleOrNull()?.get(Participants.id)?.toInt() ?: return@dbCall null
            Participants.leftJoin(SportTypes)
                .select { Participants.id eq id }
                .querySingle()
        }
    }

    suspend fun updateParticipant(id: Int, name: String?, logoUrl: String?, sportTypeId: UUID?): Participant? {
        return dbCall {
            Participants.update({ Participants.id eq id }) {
                name?.let { newValue -> it[Participants.name] = newValue }
                logoUrl?.let { newValue -> it[Participants.logoUrl] = newValue }
                sportTypeId?.let { newValue -> it[Participants.sportTypeId] = newValue }
            }
            Participants.leftJoin(SportTypes)
                .select { Participants.id eq id }
                .querySingle()
        }
    }

    suspend fun deleteParticipant(id: Int): Boolean {
        return dbCall {
            Participants.deleteWhere { Participants.id eq id }
        }?.let { it > 0 } == true
    }
}