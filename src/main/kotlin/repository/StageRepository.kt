package repository

import data.Stage
import data.StageCreateBody
import data.StageUpdateBody
import db.Stages
import org.jetbrains.exposed.sql.*

class StageRepository(database: Database) : ExposedRepository<Stages, Stage>(database) {

    override val table = Stages

    override fun ResultRow.convert(): Stage {
        return Stage(
            this[Stages.id],
            this[Stages.name],
            this[Stages.type],
        )
    }

    suspend fun getTournamentIdOfStage(id: Int): Int? {
        return dbCall {
            Stages.select { Stages.id eq id }
                .limit(1)
                .singleOrNull()
                ?.getOrNull(Stages.tournamentId)
        }
    }

    suspend fun getById(id: Int): Stage? {
        return dbCall {
            Stages.select { Stages.id eq id }
                .querySingle()
        }
    }

    suspend fun insert(body: StageCreateBody, tournamentId: Int): Stage? {
        return dbCall {
            Stages.insert {
                it[name] = body.name
                it[type] = body.type
                it[Stages.tournamentId] = tournamentId
            }.inserted()
        }
    }

    suspend fun update(id: Int, body: StageUpdateBody): Stage? {
        return dbCall {
            Stages.update({ Stages.id eq id }) {
                body.name?.let { newValue -> it[name] = newValue }
            }
            Stages.select { Stages.id eq id }.querySingle()
        }
    }

    suspend fun delete(id: Int): Boolean {
        return dbCall {
            Stages.deleteWhere { Stages.id eq id }
        }?.let { it > 0 } == true
    }
}
