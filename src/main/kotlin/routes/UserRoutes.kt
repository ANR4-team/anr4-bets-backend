package routes

import data.auth.LoginRequestBody
import data.auth.LoginResponse
import data.auth.User
import de.nielsfalk.ktor.swagger.responds
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import service.UserService
import swagger.*
import swagger.methods.get
import swagger.methods.postNoAuth
import utils.respondService

fun Routing.userRoutes() {

    val userService by inject<UserService>()

    postNoAuth<Routes.Login, LoginRequestBody>(
        "Login for Google user"
            .requestBodyExample<LoginRequestBody>()
            .noSecurity()
            .responds(
                okWithExample<LoginResponse>(),
                badRequest(),
            )
    ) { _, body ->
        println("qwe")
        val result = userService.loginUser(body)
        call.respondService(result)
    }

    authenticate {

        get<Routes.User>(
            "Get user info"
                .responds(
                    okWithExample<User>(),
                    unauthorized(),
                )
        ) { _, user ->
            call.respond(user)
        }
    }
}
