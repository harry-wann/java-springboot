package tw.harry.springboot.spring05.util

import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.Jwts
import java.util.Date

class JwtToken {

    companion object {

        private const val SECRET = "HarryWang123456789catdogfdasfadsfdasfasfsafsafsf"
        private const val EXP_TIME = 10 * 1000
        private val KEY = Keys.hmacShaKeyFor(SECRET.toByteArray())

        fun createToken(subject: String): String {
            val token = Jwts.builder()
                .subject(subject)
                .issuedAt(Date())
                .expiration(Date(System.currentTimeMillis() + EXP_TIME))
                .signWith(KEY)
                .compact()
            return token
        }

        fun parseToken(token: String): String {
            val subject = Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .payload
                .subject
            return subject
        }
    }
}