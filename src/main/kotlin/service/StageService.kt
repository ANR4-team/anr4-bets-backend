package service

import data.Stage
import data.StageCreateBody
import data.StageUpdateBody
import repository.StageRepository
import repository.TournamentRepository
import utils.Error
import utils.ServiceResult
import utils.error
import utils.success

class StageService(
    private val stageRepository: StageRepository,
    private val tournamentRepository: TournamentRepository,
) {

    suspend fun getAllStages(tournamentId: Int): ServiceResult<List<Stage>> {
        if (tournamentRepository.getById(tournamentId) == null) {
            return Error.NotFound("tournament").error()
        }
        return stageRepository.getAll()?.success() ?: Error.Unknown.error()
    }

    suspend fun getStage(stageId: Int): ServiceResult<Stage> {
        return stageRepository.getById(stageId)?.success() ?: Error.NotFound("stage").error()
    }

    suspend fun createStage(userId: String, tournamentId: Int, body: StageCreateBody): ServiceResult<Stage> {
        val tournament = tournamentRepository.getById(tournamentId) ?: return Error.NotFound("tournament").error()
        if (tournament.creator.id != userId) {
            return Error.NoAccess("tournament").error()
        }
        return stageRepository.insert(body, tournamentId)?.success() ?: Error.Create("stage").error()
    }

    suspend fun updateStage(userId: String, stageId: Int, body: StageUpdateBody): ServiceResult<Stage> {
        val tournamentIdOfStage = stageRepository.getTournamentIdOfStage(stageId) ?: return Error.NotFound("stage").error()
        val tournament = tournamentRepository.getById(tournamentIdOfStage) ?: return Error.NotFound("tournament").error()
        if (tournament.creator.id != userId) {
            return Error.NoAccess("tournament").error()
        }
        return stageRepository.update(stageId, body)?.success() ?: Error.Modify("stage").error()
    }

    suspend fun deleteStage(userId: String, stageId: Int): ServiceResult<Unit> {
        val tournamentIdOfStage = stageRepository.getTournamentIdOfStage(stageId) ?: return Error.NotFound("stage").error()
        val tournament = tournamentRepository.getById(tournamentIdOfStage) ?: return Error.NotFound("tournament").error()
        if (tournament.creator.id != userId) {
            return Error.NoAccess("tournament").error()
        }
        return if (stageRepository.delete(stageId)) {
            Unit.success()
        } else {
            Error.Delete("stage").error()
        }
    }
}
