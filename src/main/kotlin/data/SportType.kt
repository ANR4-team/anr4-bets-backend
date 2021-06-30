package data

import java.util.*

data class SportType(
    val id: UUID,
    val name: String,
) {

    companion object {
        fun example(): Map<String, Any> {
            return mapOf(
                "id" to "uuid-string",
                "name" to "football",
            )
        }
    }
}
