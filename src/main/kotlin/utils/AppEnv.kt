package utils

object AppEnv {

    fun getDatabaseUrl(): String = System.getenv("DATABASE_URL")

    val isTest: Boolean = System.getenv("TEST") != null

    val isProd: Boolean = !isTest

    object JWT {
        val secret: String = System.getenv("JWT_SECRET")
        val issuer: String = System.getenv("JWT_ISSUER")
        val realm: String = System.getenv("JWT_REALM")
    }

    val firebaseConfigFilename: String = System.getenv("FIREBASE_CONFIG_FILE_NAME")
}