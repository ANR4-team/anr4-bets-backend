package data.auth

data class LoginRequestBody(
    val token: String
) {

    companion object {
        fun example(): Map<String, Any> {
            return mapOf(
                "token" to "moqmewnfqwnufunqwufqwubf92r982bn-i-dont-know-google-token-or-some-shit",
            )
        }
    }
}
