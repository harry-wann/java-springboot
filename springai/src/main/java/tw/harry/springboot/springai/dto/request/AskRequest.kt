package tw.harry.springboot.springai.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AskRequest(
    @NotBlank
    @Size(max = 300)
    val text: String
)
