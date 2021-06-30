package data

data class SportTypeBody(val name: String) {

    companion object {
        fun example(): Map<String, Any> {
            return mapOf(
                "name" to "Dota 2"
            )
        }
    }
}