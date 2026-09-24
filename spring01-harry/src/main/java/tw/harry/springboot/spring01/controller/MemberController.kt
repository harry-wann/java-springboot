package tw.harry.springboot.spring01.controller

import org.springframework.beans.factory.annotation.Autowired
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

@RestController
@RequestMapping(value = ["/members"], produces = ["application/json"])
class MemberController(
    @Autowired
    private val memberService: MemberService
) {

    /*
        request: account=?
        response: true/false
     */
    @GetMapping("/exists")
    fun checkAccount(@RequestParam account: String): ResponseEntity<Boolean> {
        val isExists = memberService.checkAccountExists(account)
        System.out.println(isExists);
        return ResponseEntity.ok(isExists);
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
}