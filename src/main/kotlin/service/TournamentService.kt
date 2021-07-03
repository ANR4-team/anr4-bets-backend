package service

import data.Tournament
import data.TournamentCreateBody
import data.TournamentUpdateBody
import data.auth.User
import repository.SportTypeRepository
import repository.TournamentRepository
import utils.Error
import utils.ServiceResult
import utils.error
import utils.success

class TournamentService(
    private val tournamentRepository: TournamentRepository,
    private val sportTypeRepository: SportTypeRepository,
) {

    private val entityName = "tournament"

    suspend fun getAllTournaments(): ServiceResult<List<Tournament>> {
        return tournamentRepository.getAll()?.success() ?: Error.Unknown.error()
    }

    suspend fun getTournamentById(id: Int): ServiceResult<Tournament> {
        return tournamentRepository.getById(id)?.success() ?: Error.NotFound(entityName).error()
    }

    suspend fun create(user: User, createBody: TournamentCreateBody): ServiceResult<Tournament> {
        if (createBody.startDate.isAfter(createBody.endDate)) {
            return Error.Conflict("date").error()
        }
        if (sportTypeRepository.getTypeById(createBody.sportTypeId) == null) {
            return Error.NotFound("sport type").error()
        }
        return tournamentRepository.insert(createBody, user.id)?.success()
            ?: Error.Create(entityName).error()
    }

    suspend fun update(id: Int, updateBody: TournamentUpdateBody): ServiceResult<Tournament> {
        val tournament = tournamentRepository.getById(id) ?: return Error.NotFound(entityName).error()
        val startDate = updateBody.startDate ?: tournament.startDate
        val endDate = updateBody.endDate ?: tournament.endDate
        if (startDate.isAfter(endDate)) {
            return Error.Conflict("date").error()
        }
        if (updateBody.sportTypeId != null && sportTypeRepository.getTypeById(updateBody.sportTypeId) == null) {
            return Error.NotFound("sport type").error()
        }
        return tournamentRepository.update(id, updateBody)?.success()
            ?: Error.Create(entityName).error()
    }

    suspend fun delete(id: Int): ServiceResult<Unit> {
        if (tournamentRepository.getById(id) == null) {
            return Error.NotFound(entityName).error()
        }
        return if (tournamentRepository.delete(id)) {
            Unit.success()
        } else {
            Error.Delete(entityName).error()
        }
    }
}