package utils

import data.auth.User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

suspend fun PipelineContext<Unit, ApplicationCall>.getUser(): User? {
    return call.user ?: run {
        call.respond(HttpStatusCode.Unauthorized)
        null
    }
}

suspend inline fun <reified T: Any> PipelineContext<Unit, ApplicationCall>.getBody(): T? {
    return call.receiveSafe<T>() ?: run {
        call.respond(HttpStatusCode.BadRequest, "Couldn\'t parse body")
        null
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.getParameter(key: String, optional: Boolean = false): String? {
    return call.parameters[key] ?: run {
        if (!optional) {
            call.respond(HttpStatusCode.BadRequest, "Couldn\'t get parameter ($key)")
        }
        null
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.getBooleanParameter(key: String, optional: Boolean = false): Boolean? {
    return call.parameters[key]?.toBoolean() ?: run {
        if (!optional) {
            call.respond(HttpStatusCode.BadRequest, "Couldn\'t get parameter ($key)")
        }
        null
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.getIntParameter(key: String, optional: Boolean = false): Int? {
    return getParameter(key, optional)?.toIntOrNull() ?: run {
        call.respond(HttpStatusCode.BadRequest, "Couldn\'t parse parameter ($key)")
        null
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.getLongParameter(key: String, optional: Boolean = false): Long? {
    return getParameter(key, optional)?.toLongOrNull() ?: run {
        call.respond(HttpStatusCode.BadRequest, "Couldn\'t parse parameter ($key)")
        null
    }
}