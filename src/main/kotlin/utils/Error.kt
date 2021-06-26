package utils

import io.ktor.http.*

sealed class Error(val cause: String) {

    object Unknown : Error("Unknown error")

    class Custom(message: String, val code: HttpStatusCode) : Error(message)

    data class NotFound(val target: String) : Error("${target.capitalize()} not found")

    data class Create(val target: String) : Error("${target.capitalize()} creation error")

    data class Modify(val target: String) : Error("${target.capitalize()} modification error")

    data class Delete(val target: String) : Error("${target.capitalize()} deleting error")

    data class Merge(val target: String) : Error("${target.capitalize()} merging error")

    data class NoAccess(val target: String) : Error("You have no access to this ${target.lowercase()}")

    data class Conflict(val target: String) : Error("Conflict with another ${target.lowercase()}")

    data class Validation(val target: String) : Error("Validation of ${target.lowercase()} failed")

    data class Empty(val target: String) : Error("No $target were found")

    data class TooMany(val target: String) : Error("Too many ${target}s")
}