package tw.harry.springboot.spring_ai_image.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.spring_ai_image.dto.request.AskRequest
import tw.harry.springboot.spring_ai_image.dto.response.AskResponse
import tw.harry.springboot.spring_ai_image.service.AiService

@RestController
@RequestMapping("/ai")
class AiController(
    private val aiService: AiService
) {

    @PostMapping("/ask")
    fun ai(
        @Valid
        @RequestBody
        request: AskRequest
    ): AskResponse? {
        return aiService.analyze(request)
    }
}