package data.auth

import io.ktor.auth.*

data class User(
    val id: String,
    val name: String,
    val profileImageUrl: String,
) : Principal {

    companion object {
        fun example(): Map<String, Any> {
            return mapOf(
                "id" to "228322000",
                "name" to "truetripled",
                "profileImageUrl" to "https://example.com/pic.png",
            )
        }
    }
}