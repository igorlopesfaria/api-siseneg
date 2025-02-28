package br.com.finlegacy.api.core.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

object JwtConfig {
  private const val SECRET = "4u9fjD3YpA2cO7VgQ1hZ0q8rFb5sN6Tx9r1T0b5EoQ0kW4UjGZ8tH6PzL5uF1hN4" // Replace with your secret key
  private const val ISSUER = "finlegacy-api"
  private const val VALIDITY_IN_MS = 36_000_00 * 1 // 1 hour for access token
  private const val REFRESH_TOKEN_VALIDITY_IN_MS = 36_000_00 * 24 * 7 // 7 days for refreshToken token

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

//  suspend fun extractClinicId(authHeader: String, userService: UserService): Int? {
//    if (authHeader.startsWith("Bearer ")) {
//      return try {
//          val userName = verifier.verify(
//            authHeader.substringAfter("Bearer ")
//          ).getClaim("userName").asString()
//
//          val userInfo: Result<UserInfo> = userService.findByFilter(userName)
//          if (userInfo is Result.Success) {
//            userInfo.data.clinicInfo.id
//          } else { null }
//      } catch (e: Exception) { null }
//    }
//    return null
//  }

}

