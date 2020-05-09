package security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

open class DAPSJWT(secret: String) {
    private val algorithm: Algorithm = Algorithm.HMAC256(secret)
    val verifier: JWTVerifier = JWT.require(algorithm).build()

    // here you could also add to a registry, but with the time policy may not be necessary to do extra work,
    // with keeping a state registry of JWTs
    fun sign(name: String): String {
        val now: LocalDateTime = LocalDateTime.now()
        val later: LocalDateTime = now.plusHours(12)
        return JWT.create().withClaim("name", name)
            .withExpiresAt(Date.from(later.atZone(ZoneId.systemDefault()).toInstant())).sign(algorithm)
    }
}
