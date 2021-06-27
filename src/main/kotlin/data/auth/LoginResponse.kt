package data.auth

data class LoginResponse(
    val token: String,
) {

    companion object {
        fun example(): Map<String, Any> = mapOf(
            "token" to "dn2n9nsadn91n9n9sandnas",
        )
    }
}
