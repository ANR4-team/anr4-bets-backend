package service

import data.Participant
import data.ParticipantCreateBody
import data.ParticipantUpdateBody
import repository.ParticipantRepository
import repository.SportTypeRepository
import utils.Error
import utils.ServiceResult
import utils.error
import utils.success

class ParticipantService(
    private val participantRepository: ParticipantRepository,
    private val sportTypeRepository: SportTypeRepository,
) {

    private val entityName = "participant"

    suspend fun getParticipantById(id: Int): ServiceResult<Participant> {
        return participantRepository.getParticipantById(id)?.success()
            ?: Error.NotFound(entityName).error()
    }

    suspend fun getAllParticipants(): ServiceResult<List<Participant>> {
        return participantRepository.getAllParticipants()?.success()
            ?: Error.Unknown.error()
    }

    suspend fun createParticipant(body: ParticipantCreateBody): ServiceResult<Participant> {
        if (sportTypeRepository.getTypeById(body.sportTypeId) == null) {
            return Error.NotFound("sport type").error()
        }
        return participantRepository.insertParticipant(body.name, body.logoUrl, body.sportTypeId)?.success()
            ?: Error.Create(entityName).error()
    }

    suspend fun updateParticipant(id: Int, body: ParticipantUpdateBody): ServiceResult<Participant> {
        if (participantRepository.getParticipantById(id) == null) {
            return Error.NotFound(entityName).error()
        }
        if (body.sportTypeId != null && sportTypeRepository.getTypeById(body.sportTypeId) == null) {
            return Error.NotFound("sport type").error()
        }
        return participantRepository.updateParticipant(id, body.name, body.logoUrl, body.sportTypeId)?.success()
            ?: Error.Modify(entityName).error()
    }
    
    suspend fun deleteParticipant(id: Int): ServiceResult<Unit> {
        if (participantRepository.getParticipantById(id) == null) {
            return Error.NotFound(entityName).error()
        }
        return if (participantRepository.deleteParticipant(id)) {
            Unit.success()
        } else {
            Error.Delete(entityName).error()
        }
    }
}