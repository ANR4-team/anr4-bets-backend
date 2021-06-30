package utils

import data.auth.User
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import java.util.*

val ApplicationCall.user get() = authentication.principal<User>()

suspend inline fun <reified T : Any> ApplicationCall.receiveSafe(): T? {
    return try {
        receive<T>()
    } catch (e: Exception) {
        null
    }
}

suspend fun <T> ApplicationCall.respondError(error: ServiceResult.Error<T>) {
    val statusCode = when (error.error) {
        is Error.NoAccess -> HttpStatusCode.Forbidden
        is Error.NotFound -> HttpStatusCode.NotFound
        is Error.Conflict, is Error.TooMany -> HttpStatusCode.Conflict
        is Error.Validation -> HttpStatusCode.BadRequest
        is Error.Empty -> HttpStatusCode.MethodNotAllowed
        is Error.Custom -> error.error.code
        else -> HttpStatusCode.InternalServerError
    }
    respond(statusCode, error.error.cause)
}

suspend inline fun <reified T : Any> ApplicationCall.respondService(serviceResult: ServiceResult<T>) {
    when (serviceResult) {
        is ServiceResult.Error -> respondError(serviceResult)
        is ServiceResult.Success -> respond(HttpStatusCode.OK, serviceResult.data)
    }
}

val Int.seconds: Long
    get() = this * 1000L

val Int.minutes: Long
    get() = seconds * 60

val Int.hours: Long
    get() = minutes * 60

fun String.bearer(): String = "Bearer $this"

fun String.capitalize(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}

fun String.lowercase(): String {
    return lowercase(Locale.getDefault())
}

inline fun <T> tryOrNull(block: () -> T?): T? {
    return try {
        block()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
