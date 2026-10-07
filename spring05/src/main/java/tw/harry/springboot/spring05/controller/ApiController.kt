package tw.harry.springboot.spring05.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.spring05.annotation.CheckJwt
import tw.harry.springboot.spring05.dto.Login
import tw.harry.springboot.spring05.util.JwtToken

@RestController
@RequestMapping("/api")
class ApiController {

    @PostMapping("/login")
    fun login(@RequestBody login: Login): ResponseEntity<Map<String, Any>> {
        println(login)
        if ((login.account == "harry") && (login.passwd == "123456")) {
            // Login Success
            val data = String.format("%d:%s", 123, login.account)
            val jwt = JwtToken.createToken(data)
            val result = mapOf(
                "success" to true,
                "token" to jwt
            )
            return ResponseEntity.ok(result)
        } else {
            val result = mapOf("success" to false)
            return ResponseEntity.ok(result)
        }
    }

    @CheckJwt
    @RequestMapping("/main")
    fun main(): ResponseEntity<Map<String, Any>> {
        print("Data...")
        return ResponseEntity.ok(mapOf("success" to true))
    }
}