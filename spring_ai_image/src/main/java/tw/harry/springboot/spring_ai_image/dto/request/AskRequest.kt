package tw.harry.springboot.spring_ai_image.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AskRequest(
    @field:NotBlank
    val type: String,
    @field:NotBlank
    @Size(max = 8000000)
    val base64: String,
)