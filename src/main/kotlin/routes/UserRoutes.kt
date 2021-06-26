package routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.userRoutes() {

    get("/") {
        call.respond("Hello")
    }

    // todo: declare routes here
}