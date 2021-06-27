package utils

object AppEnv {

    fun getDatabaseUrl(): String = System.getenv("DATABASE_URL")

    private val envType: String = System.getenv("ENV")

    val isTest: Boolean = envType == "TEST"

    val isProd: Boolean = envType == "PROD"

    object JWT {
        val secret: String = System.getenv("JWT_SECRET")
        val issuer: String = System.getenv("JWT_ISSUER")
        val realm: String = System.getenv("JWT_REALM")
    }
}
