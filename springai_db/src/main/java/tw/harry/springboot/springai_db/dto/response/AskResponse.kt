package tw.harry.springboot.springai_db.dto.response

data class AskResponse(
    val answer: String?,
    val dataSource: String,
    val toolCalls: List<ToolTrace>
)
