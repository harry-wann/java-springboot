package tw.harry.springboot.spring01.controller

import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.spring01.entitiy.Member
import tw.harry.springboot.spring01.service.MemberService
import java.util.Objects

@RestController
@RequestMapping(value = ["/members"], produces = ["application/json"])
class MemberController(
    @Autowired
    private val memberService: MemberService,
    @Value("\${company.name}")
    private val companyName: String,
    @Value("\${company.tel}")
    private val companyTel: String,
) {

    /*
        request: account=?
        response: true/false
     */
    @GetMapping("/exists")
    fun checkAccount(@RequestParam account: String): ResponseEntity<Map<String, Boolean>> {
        val isExists = memberService.checkAccountExists(account)
        return ResponseEntity.ok(mapOf("isExists" to isExists))
    }

    /*
        request: member object
        response: { "success": true/false }
     */
    @PostMapping("/register")
    fun register(@RequestBody member: Member): ResponseEntity<Map<String, Boolean>> {
        val isSuccess = memberService.registerMember(member)
        return ResponseEntity.ok(mapOf("success" to isSuccess))
    }

    /*
        request: { account: xxx, passwd: xxx }
        response: { "success": true/false }
     */
    @PostMapping("/login")
    fun login(@RequestBody body: Map<String, String>): ResponseEntity<Map<String, Boolean>> {
        val account = body["account"] as String
        val passwd = body["passwd"] as String
        val isSuccess = memberService.login(account, passwd)
        return ResponseEntity.ok(mapOf("success" to isSuccess))
    }

    @PostMapping("/loginV2")
    fun login(
        @RequestBody body: Map<String, String>,
        session: HttpSession
    ): ResponseEntity<Map<String, Boolean>> {
        val account = body["account"] as String
        val passwd = body["passwd"] as String
        val member = memberService.login3(account, passwd)

        var isSuccess = false
        if (member != null) {
            session.setAttribute("member", member)
            isSuccess = true
        } else {
            session.invalidate()
        }

        return ResponseEntity.ok(mapOf("success" to isSuccess))
    }

    @GetMapping("/logout")
    fun logout(session: HttpSession) {
        session.invalidate()
    }

    @PostMapping("/status")
    fun status(session: HttpSession): ResponseEntity<Map<String, Any>> {
        val member = session.getAttribute("member")
        val map = HashMap<String, Any>()
        map["success"] = member != null
        map["member"] = member
        map["companyName"] = companyName
        map["companyTel"] = companyTel
        return ResponseEntity.ok(map)
    }
}