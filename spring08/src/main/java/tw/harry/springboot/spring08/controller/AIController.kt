package tw.harry.springboot.spring08.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.spring08.dto.TextRequest
import tw.harry.springboot.spring08.dto.TextResponse
import tw.harry.springboot.spring08.service.AITextService

@RequestMapping("/api")
@RestController
class AIController(
    private val service: AITextService
) {

    @GetMapping("/active")
    fun active(): Map<String, String> {
        return mapOf("status" to "up")
    }


    @PostMapping("/ai/describeProduct")
    fun describeProduct(
        @RequestBody
        request: TextRequest
    ): TextResponse {
        return TextResponse(service.describeProduct(request.text))
    }

    @PostMapping("/ai/complain")
    fun complain(
        @RequestBody
        request: TextRequest
    ): TextResponse {
        return TextResponse(service.complain(request.text))
    }
}