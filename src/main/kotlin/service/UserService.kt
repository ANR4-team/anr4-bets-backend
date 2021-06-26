package service

import repository.UserRepository

class UserService(
    private val userRepository: UserRepository,
) {
}