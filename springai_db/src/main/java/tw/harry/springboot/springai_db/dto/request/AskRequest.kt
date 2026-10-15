package tw.harry.springboot.springai_db.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AskRequest(
    @NotBlank
    @Size(max = 4000)
    val text: String
)