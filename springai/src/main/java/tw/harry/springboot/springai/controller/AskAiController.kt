package tw.harry.springboot.springai.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.springai.dto.request.AskRequest
import tw.harry.springboot.springai.dto.response.AskResponse
import tw.harry.springboot.springai.service.ProductAiService

@RequestMapping("/ai")
@RestController
class AskAiController(
    private val service: ProductAiService
) {

    @PostMapping("/product")
    fun ask(
        @RequestBody request: AskRequest,
    ): AskResponse {
        return service.ask(request.text)
    }
}