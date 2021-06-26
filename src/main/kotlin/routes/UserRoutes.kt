package routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.userRoutes() {

    get("/") {
        call.respond("Hello")
    }

    get("/cock") {
        call.respond("YEP")
    }

    // todo: declare routes here
}