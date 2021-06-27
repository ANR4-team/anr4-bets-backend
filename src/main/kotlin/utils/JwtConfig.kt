package utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

object JwtConfig {

    private val algorithm = Algorithm.HMAC512(AppEnv.JWT.secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(AppEnv.JWT.issuer)
        .build()

    fun makeAccessToken(userId: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(AppEnv.JWT.issuer)
        .withClaim("id", userId)
        .sign(algorithm)
}