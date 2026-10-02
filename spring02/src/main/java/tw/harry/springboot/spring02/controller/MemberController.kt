package tw.harry.springboot.spring02.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.spring02.entity.Info
import tw.harry.springboot.spring02.entity.Member
import tw.harry.springboot.spring02.service.MemberService

@RequestMapping("/members")
@RestController
class MemberController(
    @Autowired val service: MemberService
) {

    /*
        POST: /members
        data: {
            account: "xxx",
            passwd: "xxx",
            info: {
                tel: "xxx",
                name: "xxx",
                gender: true/false
            }
        }
    */
    @PostMapping("")
    fun addMember(@RequestBody data: Map<String, Any>): ResponseEntity<Member> {
        val member = Member()
        member.account = data["account"].toString()
        member.passwd = data["passwd"].toString()

        val info = Info()
        val infoData: Map<String, Any>? = data["info"] as? Map<String, Any>
        if (infoData != null) {
            info.isMale = infoData["gender"].toString().toBoolean()
            info.tel = infoData["tel"].toString()
            info.name = infoData["name"].toString()
        }

        val savedMember = service.save(member, info)
        return ResponseEntity.ok(savedMember)
    }

    @PutMapping("/{memberId}/info")
    fun setInfoToMember(
        @PathVariable("memberId") memberId: Long,
        @RequestBody data: Map<String, Any>
    ): ResponseEntity<Info> {
        val info = Info()
        info.isMale = data["isMale"].toString().toBoolean()
        info.tel = data["tel"].toString()
        info.name = data["name"].toString()

        val savedInfo = service.saveInfoToMember(memberId, info)
        return ResponseEntity.ok(savedInfo)
    }

    @GetMapping("/{memberId}")
    fun queryMemberById(@PathVariable("memberId") memberId: Long): ResponseEntity<Member> {
        return ResponseEntity.ok(service.findMemberById(memberId))
    }
}