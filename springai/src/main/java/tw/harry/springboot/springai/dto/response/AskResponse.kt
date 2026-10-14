package tw.harry.springboot.springai.dto.response

data class AskResponse(
    val answer: String? = null,
    val datasource: String? = null,
    val toolCalls: List<TotalTrace>? = null,
)
