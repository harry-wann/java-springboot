package tw.harry.springboot.spring04.controller

import org.mindrot.jbcrypt.BCrypt
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.spring04.dto.LoginDto
import tw.harry.springboot.spring04.repository.MemberRepo
import tw.harry.springboot.spring04.response.LoginResponse
import tw.harry.springboot.spring04.util.JwtToken

@RequestMapping("/members")
@RestController
@CrossOrigin(origins = ["http://localhost:5173"])
class MemberController(
    val repo: MemberRepo
) {
    @PostMapping("/login")
    fun login(@RequestBody login: LoginDto): ResponseEntity<Map<String, Any>> {
        val response = HashMap<String, Any>()
        val member = repo.findByAccount(login.account).orElse(null)
        if (member == null) {
            response["success"] = false
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
        }
        if (!BCrypt.checkpw(login.passwd, member.passwd)) {
            response["success"] = false
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
        }
        member.passwd = ""
        val token = JwtToken.createToken("${member.id}:${login.account}")
        response["success"] = true
        response["member"] = member
        response["token"] = token
        return ResponseEntity.ok(response)
    }
}