package tw.harry.springboot.spring01.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.spring01.entity.Gift
import tw.harry.springboot.spring01.repository.GiftRepository
import tw.harry.springboot.spring01.repository.MemberRepository

@RequestMapping("/gifts")
@RestController
class GiftController(
    @Autowired val giftRepository: GiftRepository
) {

    @GetMapping
    fun queryGiftByPage(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "pageSize", defaultValue = "10") pageSize: Int,
    ): ResponseEntity<Map<String, Any>> {
        val pageable: Pageable = PageRequest.of(page, pageSize)
        val giftPage: Page<Gift> = giftRepository.findAll(pageable)

        val result: Map<String, Any> = mapOf(
            "data" to giftPage.content,
            "total" to giftPage.totalElements,
            "totalPages" to giftPage.totalPages,
            "page" to giftPage.number,
            "isLast" to giftPage.isLast,
        )

        System.out.println(result.toString())

        return ResponseEntity.ok(result)
    }

}