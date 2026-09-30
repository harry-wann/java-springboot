package tw.harry.springboot.spring01.controller

import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import tw.harry.springboot.spring01.config.ReadConfig
import tw.harry.springboot.spring01.dto.Base64Upload
import tw.harry.springboot.spring01.dto.MemberForm
import tw.harry.springboot.spring01.entity.Member
import tw.harry.springboot.spring01.service.MemberService
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.Base64

@RestController
@RequestMapping(value = ["/members"], produces = ["application/json"])
class MemberController(
    @Autowired
    private val memberService: MemberService,
    @Value("\${company.name}")
    private val companyName: String,
    @Value("\${company.tel}")
    private val companyTel: String,
    @Autowired
    private val jdbc: NamedParameterJdbcTemplate,
    @Autowired
    private val readConfig: ReadConfig,
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

    @PostMapping("/{id}")
    fun upload(
        @PathVariable id: Long,
        @RequestParam upload: MultipartFile,
    ) {
        try {
            val sql = """
                UPDATE member
                SET icon = :icon
                WHERE id = :id
            """.trimIndent()
            val args = HashMap<String, Any>()
            args["id"] = id
            args["icon"] = upload.bytes
            val n = jdbc.update(sql, args)
            System.out.println(n)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @PostMapping("/test2")
    fun test2(
        @ModelAttribute memberForm: MemberForm,
    ) {
        try {
            System.out.println(memberForm.account)
            System.out.println(memberForm.files.size)

            val here = File(".")

            for (file in memberForm.files) {
                if (!file.isEmpty) {
                    file.transferTo(
                    File(here.absolutePath
                            + "/"
                            + readConfig.getUploadDir()
                            + "/"
                            + memberForm.account
                            + "_" + file.originalFilename
                        )
                    )
                }
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @PostMapping("/test3")
    fun test3(@RequestBody upload: Base64Upload): ResponseEntity<String> {
        System.out.println(upload.fileName)
        System.out.println(upload.contentType)
        System.out.println(upload.base64)

        /*
        * Save Table:
        *   1. String : upload.base64
        *   2. blob: fileBytes
        *
        * Save file: fileBytes
        * */

        val fileByte = Base64.getDecoder().decode(upload.base64)
        val uploadDir = Path.of(readConfig.getUploadDir())
        val filePath = uploadDir.resolve(upload.fileName)


        try {
            Files.write(filePath, fileByte)
            return ResponseEntity.ok("Upload success!")
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }
}