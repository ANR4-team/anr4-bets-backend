package service

import com.google.firebase.auth.FirebaseAuth
import data.auth.UserModel
import io.ktor.http.*
import repository.UserRepository
import utils.*

class UserService(
    private val userRepository: UserRepository,
) {

    private val firebaseAuth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    suspend fun loginUser(idToken: String): ServiceResult<UserModel> {
        val verifiedToken = firebaseAuth.verifyToken(idToken)
            ?: return Error.Custom("Couldn\'t verify this token", HttpStatusCode.Unauthorized).error()
        val uid = verifiedToken.uid
        val googleUser = firebaseAuth.getGoogleUser(uid)
            ?: return Error.Custom("Couldn\'t get data about user", HttpStatusCode.Unauthorized).error()
        val user = userRepository.getUser(uid)
            ?: userRepository.createUser(uid, googleUser.displayName, googleUser.photoUrl)
            ?: return Error.Unknown.error()
        val jwtToken = JwtConfig.makeAccessToken(uid)
        return UserModel(user, jwtToken).success()
    }
}
