package tw.harry.springboot.springai_db.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.springai_db.dto.request.AskRequest
import tw.harry.springboot.springai_db.dto.response.AskResponse
import tw.harry.springboot.springai_db.service.AiService

@RestController
@RequestMapping("/ai")
class AiController(
    private val service: AiService,
) {


    @PostMapping("/ask")
    fun ask(
        @RequestBody
        request: AskRequest
    ): AskResponse {
        return service.ask(request.text)
    }

}