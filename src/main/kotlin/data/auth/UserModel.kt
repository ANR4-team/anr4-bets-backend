package data.auth

data class UserModel(
    val user: User,
    val token: String,
) {

    companion object {
        fun example(): Map<String, Any> = mapOf(
            "user" to User.example(),
            "token" to "dn2n9nsadn91n9n9sandnas",
        )
    }
}
