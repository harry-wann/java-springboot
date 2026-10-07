package tw.harry.springboot.spring04.controller

import io.jsonwebtoken.Jwts
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.spring04.dto.LoginDto
import tw.harry.springboot.spring04.repository.MemberRepo
import tw.harry.springboot.spring04.response.LoginResponse
import tw.harry.springboot.spring04.util.JwtToken
import kotlin.math.log

@RequestMapping("/auth")
@RestController
class AuthController(
    private val repo: MemberRepo
) {

    @PostMapping("/login")
    fun login(@RequestBody login: LoginDto): ResponseEntity<Any> {
        val member = repo.findByAccount(login.account).orElse(null)
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("帳號錯誤")
        }
        System.out.println(BCrypt.hashpw(login.passwd, BCrypt.gensalt()))
        if (!BCrypt.checkpw(login.passwd, member.passwd)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("密碼錯誤")
        }
        val token = JwtToken.createToken("${member.id}:${login.account}")
        return ResponseEntity.ok(LoginResponse(token, member.account, member.name))
    }

    @PostMapping("/api/test1")
    fun test1(@RequestHeader header: Map<String, String>): ResponseEntity<String> {
        val auth = header["Authorization"]
        if (auth.isNullOrEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("密碼錯誤")
        }
        val token = auth.split(" ").getOrNull(1)
        if (token.isNullOrEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("密碼錯誤")
        }
        val subject = JwtToken.parseToken(token)
        return ResponseEntity.ok(subject)
    }
}