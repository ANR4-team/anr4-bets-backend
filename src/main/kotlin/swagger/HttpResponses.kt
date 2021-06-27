package swagger

import de.nielsfalk.ktor.swagger.HttpCodeResponse
import de.nielsfalk.ktor.swagger.Metadata
import de.nielsfalk.ktor.swagger.security
import io.ktor.http.*

fun String.noSecurity(): Metadata = security(emptyMap())

fun badRequest(description: String? = null) = HttpCodeResponse(
    HttpStatusCode.BadRequest,
    emptyList(),
    description ?: "Parameters or body parsing error"
)

fun unauthorized(description: String? = null) = HttpCodeResponse(
    HttpStatusCode.Unauthorized,
    emptyList(),
    description ?: "Something wrong happened to auth token or user data"
)

inline fun <reified T> notFound(entityName: String? = null) = HttpCodeResponse(
    HttpStatusCode.NotFound,
    emptyList(),
    "${entityName ?: T::class.java.simpleName} not found"
)

fun notAllowed(description: String? = null) = HttpCodeResponse(
    HttpStatusCode.MethodNotAllowed,
    emptyList(),
    description ?: "Method not allowed"
)

fun conflict(description: String? = null) = HttpCodeResponse(
    HttpStatusCode.Conflict,
    emptyList(),
    description ?: "Conflict"
)

fun methodNotAllowed(description: String? = null) = HttpCodeResponse(
    HttpStatusCode.MethodNotAllowed,
    emptyList(),
    description ?: "Method not allowed"
)