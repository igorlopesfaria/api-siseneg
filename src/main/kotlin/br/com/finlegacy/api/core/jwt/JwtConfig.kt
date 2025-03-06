package br.com.finlegacy.api.core.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import java.util.Date

object JwtConfig {
  private val config = ConfigFactory.load()

  private val SECRET = config.getString("ktor.jwt.secret")
  private val ISSUER = config.getString("ktor.jwt.issuer")
  private val VALIDITY_IN_MS = config.getLong("ktor.jwt.accessTokenValidityMs")
  private val REFRESH_TOKEN_VALIDITY_IN_MS = config.getLong("ktor.jwt.refreshTokenValidityMs")


  fun generateAccessToken(uid: String): String = JWT.create()
    .withIssuer(ISSUER)
    .withClaim("uid", uid)
    .withExpiresAt(Date(System.currentTimeMillis() + VALIDITY_IN_MS))
    .sign(Algorithm.HMAC256(SECRET))

  fun generateRefreshToken(uid: String): String = JWT.create()
    .withIssuer(ISSUER)
    .withClaim("uid", uid)
    .withExpiresAt(Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_IN_MS))
    .sign(Algorithm.HMAC256(SECRET))

  val verifier: JWTVerifier = JWT.require(Algorithm.HMAC256(SECRET))
    .withIssuer(ISSUER)
    .build()

  fun extractUid(authHeader: String): String? {
    if (authHeader.startsWith("Bearer ")) {
      return try {
        verifier.verify(
          authHeader.substringAfter("Bearer ")
        ).getClaim("uid").asString()
      } catch (e: Exception) { null }
    }
    return null
  }
}

