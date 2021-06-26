package utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtConfig {

    private val algorithm = Algorithm.HMAC512(AppEnv.JWT.secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(AppEnv.JWT.issuer)
        .build()

    fun makeAccessToken(userId: String, time: Long): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(AppEnv.JWT.issuer)
        .withClaim("id", userId)
        .apply {
            withClaim("refreshDate", time)
            withExpiresAt(getExpiration(time))
        }
        .sign(algorithm)

    fun makeRefreshToken(): String {
        return UUID.randomUUID().toString().take(32)
    }

    private fun getExpiration(time: Long) = Date(time + 24.hours)
}