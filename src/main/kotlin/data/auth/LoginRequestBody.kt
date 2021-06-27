package data.auth

data class LoginRequestBody(
    val id: String,
    val name: String,
    val profileImageUrl: String,
) {

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
