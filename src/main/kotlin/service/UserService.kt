package service

import data.auth.LoginRequestBody
import data.auth.LoginResponse
import repository.UserRepository
import utils.*

class UserService(
    private val userRepository: UserRepository,
) {

    suspend fun loginUser(requestBody: LoginRequestBody): ServiceResult<LoginResponse> {
        if (userRepository.getUser(requestBody.id) == null) {
            userRepository.createUser(requestBody.id, requestBody.name, requestBody.profileImageUrl)
        } else {
            userRepository.updateUser(requestBody.id, requestBody.name, requestBody.profileImageUrl)
        } ?: return Error.Unknown.error()
        val jwtToken = JwtConfig.makeAccessToken(requestBody.id)
        return LoginResponse(jwtToken).success()
    }
}
