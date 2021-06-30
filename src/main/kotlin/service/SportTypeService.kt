package service

import data.SportType
import repository.SportTypeRepository
import utils.Error
import utils.ServiceResult
import utils.error
import utils.success
import java.util.*

class SportTypeService(
    private val sportTypeRepository: SportTypeRepository,
) {

    suspend fun getAllSportTypes(): ServiceResult<List<SportType>> {
        return sportTypeRepository.getAllTypes().success()
    }

    suspend fun createSportType(name: String): ServiceResult<SportType> {
        if (sportTypeRepository.getTypeByName(name) != null) {
            return Error.Conflict("sport type").error()
        }
        return sportTypeRepository.insertType(name)?.success() ?: Error.Create("sport type").error()
    }

    suspend fun updateSportType(id: UUID, newName: String): ServiceResult<SportType> {
        if (sportTypeRepository.getTypeById(id) == null) {
            return Error.NotFound("sport type").error()
        }
        if (sportTypeRepository.getTypeByName(newName) != null) {
            return Error.Conflict("sport type").error()
        }
        return sportTypeRepository.updateType(id, newName)?.success() ?: Error.Modify("sport type").error()
    }

    suspend fun deleteSportType(id: UUID): ServiceResult<Unit> {
        if (sportTypeRepository.getTypeById(id) == null) {
            return Error.NotFound("sport type").error()
        }
        return if (sportTypeRepository.deleteType(id)) {
            Unit.success()
        } else {
            Error.Delete("sport type").error()
        }
    }
}