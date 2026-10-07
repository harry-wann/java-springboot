package tw.harry.springboot.spring04.controller

import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.spring04.repository.GiftRepo
import tw.harry.springboot.spring04.util.JwtToken
import kotlin.text.isNullOrEmpty
import kotlin.text.split

@RequestMapping("/gifts")
@RestController
@CrossOrigin(origins = ["http://localhost:5173"])
class GiftController(
    private val repo: GiftRepo
) {

    @GetMapping
    fun queryGifts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestHeader(name = "Authorization") authorization: String = ""
    ): ResponseEntity<Map<String, Any>> {
        try {
            val token = authorization.split(" ").getOrNull(1)
            System.out.println(authorization)
            if (token.isNullOrEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
            }
            val subject = JwtToken.parseToken(token)

            val pageable = PageRequest.of(page, pageSize)
            val data = repo.findAll(pageable)
            val result = mapOf(
                "data" to data.content,
                "total" to data.totalElements,
                "totalPage" to data.totalPages,
                "page" to data.number,
                "isLast" to data.isLast,
            )
            return ResponseEntity.ok(result)
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.badRequest().body(mapOf("success" to "false"))
        }
    }
}