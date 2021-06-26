package utils

object AppEnv {

    fun getDatabaseUrl(): String = System.getenv("DATABASE_URL")

    val isTest = System.getenv("TEST") != null

    val isProd = !isTest

    object JWT {
        val secret: String = System.getenv("JWT_SECRET")
        val issuer: String = System.getenv("JWT_ISSUER")
        val realm: String = System.getenv("JWT_REALM")
    }
}