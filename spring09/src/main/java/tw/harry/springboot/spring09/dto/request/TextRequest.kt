package tw.harry.springboot.spring09.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class TextRequest(
    @NotBlank
    @Size(max = 100)
    val text: String
)
