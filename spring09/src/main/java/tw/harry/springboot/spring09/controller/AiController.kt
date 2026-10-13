package tw.harry.springboot.spring09.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.spring09.dto.request.TextRequest
import tw.harry.springboot.spring09.dto.response.ProductCriteria
import tw.harry.springboot.spring09.dto.response.Result
import tw.harry.springboot.spring09.service.StructuredAiService

@RequestMapping("/ai")
@RestController
class AiController(
    private val service: StructuredAiService
) {

    @PostMapping("/product")
    fun parse(
        @Valid
        @RequestBody
        request: TextRequest
    ): Result<ProductCriteria?> {
        return Result(service.parseProductCriteria(request.text))
    }


}