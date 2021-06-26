package swagger

import de.nielsfalk.ktor.swagger.HttpCodeResponse
import io.ktor.http.*

fun badRequest(description: String? = null) = HttpCodeResponse(
    HttpStatusCode.BadRequest,
    emptyList(),
    description ?: "Parameters or body parsing error"
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